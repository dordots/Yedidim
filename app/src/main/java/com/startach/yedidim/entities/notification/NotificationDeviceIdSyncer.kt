package com.startach.yedidim.entities.notification

import com.google.firebase.iid.FirebaseInstanceId
import com.startach.yedidim.entities.usermanagement.UserManager
import com.startach.yedidim.network.VolunteerApi
import com.startach.yedidim.utils.empty
import io.reactivex.Completable
import io.reactivex.Single


class NotificationDeviceIdSyncer(private val volunteerApi: VolunteerApi, private val userManager: UserManager) {

    fun syncDeviceID(): Completable {
        return userManager.getCurrentUser()
                .flatMapCompletable { user ->
                    val phoneNum: String = user.mobilePhone ?: throw Exception("user mobilePhone is null")
                    return@flatMapCompletable Single.fromCallable {
                        return@fromCallable FirebaseInstanceId.getInstance().token ?: throw Exception("Instance ID token isn't initialized")
                    }
                            .flatMapCompletable { instanceId ->
                                volunteerApi.updateInstanceId(phoneNum, instanceId)
                                        .doOnComplete {
                                            userManager.active = true
                                        }
                            }
                }
    }

    fun resetDeviceID(): Completable {
        return userManager.getCurrentUser()
                .map { user ->
                    val phoneNum: String = user.mobilePhone ?: throw Exception("user mobilePhone is null")
                    return@map phoneNum
                }
                .flatMapCompletable { phoneNum ->
                    volunteerApi.updateInstanceId(phoneNum, String.empty).doOnComplete {
                        userManager.active = false
                    }
                }
    }
}