package com.startach.yedidim.entities.notification

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.startach.yedidim.Model.Event
import timber.log.Timber


class YedidimFirebaseNotification : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        Timber.d("From: " + remoteMessage.from)
        if (remoteMessage.data.isNotEmpty()) {
            Timber.d("Message data payload: " + remoteMessage.data)
            val rawEvent = remoteMessage.data["event"]
            rawEvent.let {
                val gson = Gson()
                val event = gson.fromJson(it, Event::class.java)
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            Timber.d("Message Notification Body: " + remoteMessage.notification.body!!)
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()

        //TODO
    }
}