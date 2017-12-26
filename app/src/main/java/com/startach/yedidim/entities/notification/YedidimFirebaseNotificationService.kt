package com.startach.yedidim.entities.notification

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.startach.yedidim.Model.Event
import timber.log.Timber


class YedidimFirebaseNotificationService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        Timber.d("From: " + remoteMessage.from)
        if (remoteMessage.data.isNotEmpty()) {
            Timber.d("Message data payload: " + remoteMessage.data)
            val rawEvent = remoteMessage.data["event"]
            rawEvent.let {
                val gson = Gson()
                val event = gson.fromJson<Event>(it, Event::class.java)
                event.let {
                    NotificationsGenerator(this,it).notifyNow()
                }
            }
        }
    }

}