package com.startach.yedidim.entities.notification

import android.content.Context
import android.support.v4.app.NotificationManagerCompat
import com.startach.yedidim.Model.Event


class EventNotificationEntity(private val context: Context) {

    fun dismissNotification(event: Event) {
        NotificationManagerCompat.from(context)
                .cancel(event.hashCode())
    }

}