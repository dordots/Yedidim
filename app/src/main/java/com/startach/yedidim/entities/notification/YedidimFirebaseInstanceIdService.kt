package com.startach.yedidim.entities.notification

import com.google.firebase.iid.FirebaseInstanceIdService
import com.startach.yedidim.modules.App
import timber.log.Timber
import javax.inject.Inject


class YedidimFirebaseInstanceIdService : FirebaseInstanceIdService() {

    @Inject lateinit var notificationDeviceIdSyncer: NotificationDeviceIdSyncer

    override fun onCreate() {
        super.onCreate()
        (application as App).component.inject(this)
    }

    override fun onTokenRefresh() {
        notificationDeviceIdSyncer.syncDeviceID()
                .onErrorComplete { t ->
                    Timber.e(t, "can't refresh token")
                    return@onErrorComplete true
                }
                .subscribe()
    }
}