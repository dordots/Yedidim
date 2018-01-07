package com.startach.yedidim.entities.usermanagement

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.startach.yedidim.Model.Volunteer
import com.startach.yedidim.network.VolunteerApi
import com.startach.yedidim.utils.empty
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject

class UserManager(context: Context, private val volunteerApi: VolunteerApi) {

    companion object {
        private val SHARED_PREF_KEY_CURRENT_USER = "SHARED_PREF_KEY_CURRENT_USER"
        private val SHARED_PREF_KEY_ACTIVE = "SHARED_PREF_KEY_ACTIVE"
    }
    private val pref: SharedPreferences = context.getSharedPreferences(this.javaClass.simpleName, Context.MODE_PRIVATE)

    var active: Boolean
        get() = pref.getBoolean(SHARED_PREF_KEY_ACTIVE, false)
        set(value) {
            pref.edit().putBoolean(SHARED_PREF_KEY_ACTIVE, value).apply()
            activeStateSubject.onNext(value)
        }
    val activeStateSubject : BehaviorSubject<Boolean> = BehaviorSubject.createDefault(active)

    private var currentUser: Volunteer? = null

    fun getCurrentUser(): Single<Volunteer> {
        return Single.defer {
            if (currentUser == null) {
                val currentUserJson = pref.getString(SHARED_PREF_KEY_CURRENT_USER, String.empty)
                if (currentUserJson.isEmpty()) {
                    return@defer extractCurrentUser()
                            .doOnSuccess(this::cacheCurrentUser)
                            .onErrorReturn { Volunteer() }
                } else {
                    return@defer parseJson(currentUserJson)
                            .doOnSuccess(this::cacheCurrentUser)
                }
            } else {
                return@defer Single.just(currentUser)
            }
        }
    }

    private fun extractCurrentUser(): Single<Volunteer> {
        val phoneNum = FirebaseAuth.getInstance().currentUser?.phoneNumber ?: throw Exception("current user phone num isn't available")
        return volunteerApi.volunteerByPhone(phoneNum)
    }

    private fun parseJson(userJson: String): Single<Volunteer> {
        return Single.fromCallable {
            val gson = Gson()
            return@fromCallable gson.fromJson(userJson, Volunteer::class.java)
        }
    }

    private fun cacheCurrentUser(user: Volunteer) {
        currentUser = user.copy(fcmToken = null) //don't cache FCM token! should remain private
        val gson = Gson()
        pref.edit()
                .putString(SHARED_PREF_KEY_CURRENT_USER, gson.toJson(user))
                .apply()
    }

}

