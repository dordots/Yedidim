package com.startach.yedidim.entities.notification

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.startach.yedidim.EventInfoActivity
import com.startach.yedidim.Model.Event
import com.startach.yedidim.Model.displayableCase
import com.startach.yedidim.R

/**
 * Created by yb34982 on 26/12/2017.
 */
class NotificationsGenerator(val context: Context, val event: Event) {
    val GROUP_KEY_EVENTS = "group_key_events"
    val notification : Notification
    init {
        val intent = EventInfoActivity.createIntent(context, event)
        //TODO using hashcode() is not ideal
        val pendingIntent = PendingIntent.getActivity(context, event.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val case = event.displayableCase(context.resources)

        notification = NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources,R.mipmap.ic_launcher))
                .setSound(Uri.parse("android.resource://"+context.packageName +"/"+R.raw.car_alarm))
                .setContentIntent(pendingIntent)
                .setContentTitle(context.getString(R.string.notif_event_title, case))
                .setContentText(event.details?.fullAddress)
                .setGroup(GROUP_KEY_EVENTS)
                .setGroupSummary(false)
                .setAutoCancel(true)
                .build()

    }

    fun notifyNow(){
        NotificationManagerCompat.from(context)
                .notify(event.hashCode(), notification)
    }
}