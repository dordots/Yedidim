package com.startach.yedidim.entities.notification

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.startach.yedidim.EventInfoActivity
import com.startach.yedidim.Model.Event
import com.startach.yedidim.R
import com.startach.yedidim.utils.empty
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
                    val intent = EventInfoActivity.Companion.createIntent(this, event)
                    //TODO using hashcode() is not ideal
                    val pendingIntent = PendingIntent.getActivity(this, event.hashCode(), intent, FLAG_UPDATE_CURRENT)
                    val case = extractCase(event)


                    val notification = NotificationCompat.Builder(this)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentIntent(pendingIntent)
                            .setContentTitle(getString(R.string.notif_event_title, case))
                            .setContentText(event.details?.fullAddress)
                            .build()
                    NotificationManagerCompat.from(this)
                            .notify(getNotificationID(), notification)
                }
            }
        }
    }

    private fun extractCase(event: Event): String? {
        var case: String? = String.empty
        val events = resources.getStringArray(R.array.event_cases)
        val eventCase = event.details?.case
        eventCase?.let {
            if (events.indices.contains(eventCase)) {
                case = events[eventCase]
            }
        }
        return case
    }

    private fun getNotificationID(): Int {
        val sharedPref = getSharedPreferences("notification", Context.MODE_PRIVATE)
        val id = sharedPref.getInt("id", 0)
        sharedPref.edit().putInt("id", id + 1).apply()
        return id
    }
}