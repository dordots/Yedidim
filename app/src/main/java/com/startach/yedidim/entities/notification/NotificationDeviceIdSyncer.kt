package com.startach.yedidim.entities.notification

import com.google.firebase.iid.FirebaseInstanceId
import com.startach.yedidim.entities.usermanagement.UserManager
import com.startach.yedidim.network.VolunteerApi
import io.reactivex.Completable
import io.reactivex.Single


class NotificationDeviceIdSyncer(private val volunteerApi: VolunteerApi, private val userManager: UserManager) {

    fun syncDeviceID(): Completable {
        return userManager.getCurrentUser()
                .flatMapCompletable { user ->
                    val userId: String = user.id ?: throw Exception("user id is null")
                    return@flatMapCompletable Single.fromCallable {
                        return@fromCallable FirebaseInstanceId.getInstance().token ?: throw Exception("Instance ID token isn't initialized")
                    }
                            .flatMapCompletable { instanceId -> volunteerApi.updateInstanceId(userId, instanceId) }
                }
    }
}