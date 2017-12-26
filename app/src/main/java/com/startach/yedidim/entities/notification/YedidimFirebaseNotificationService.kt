package com.startach.yedidim.entities.notification

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.startach.yedidim.EventInfoActivity
import com.startach.yedidim.Model.Event
import com.startach.yedidim.Model.displayableCase
import com.startach.yedidim.R
import timber.log.Timber


class YedidimFirebaseNotificationService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        Timber.d("From: " + remoteMessage.from)
        if (remoteMessage.data.isNotEmpty()) {
            Timber.d("Message data payload: " + remoteMessage.data)
            val rawEvent = remoteMessage.data["event"]
            rawEvent.let {
                Timber.d("Incoming event Json : %s", it)
                val gson = Gson()
                val event = gson.fromJson<Event>(it, Event::class.java)
                Timber.d("Event parsed successfully")
                event.let {
                    val intent = EventInfoActivity.Companion.createIntent(this, event)
                    //TODO using hashcode() is not ideal
                    val pendingIntent = PendingIntent.getActivity(this, event.hashCode(), intent, FLAG_UPDATE_CURRENT)
                    val case = event.displayableCase(resources)

                    val notification = NotificationCompat.Builder(this)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentIntent(pendingIntent)
                            .setContentTitle(getString(R.string.notif_event_title, case))
                            .setContentText(event.details?.fullAddress)
                            .build()
                    NotificationManagerCompat.from(this)
                            .notify(event.hashCode(), notification)
                }
            }
        }
    }
}