package com.startach.yedidim.entities.notification

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.squareup.picasso.Picasso
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
        val (address, bigPictureStyle, summaryText) = prepareNotificationStyleAndText()

        notification = NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources,R.mipmap.ic_launcher))
                .setSound(Uri.parse("android.resource://"+context.packageName +"/"+R.raw.car_alarm))
                .setContentIntent(pendingIntent)
                .setContentText(summaryText)
                .setContentTitle(address)
                .setStyle(bigPictureStyle)
                .setGroup(GROUP_KEY_EVENTS)
                .setGroupSummary(false)
                .setAutoCancel(true)
                .build()

    }

    private fun prepareNotificationStyleAndText(): Triple<String, NotificationCompat.BigPictureStyle, String> {
        val case = event.displayableCase(context.resources)
        val address = event.details?.address ?: ""
        val carModel = event.details?.carType ?: "לא ידוע"
        val geo = event.details?.geo
        val cords = "${geo?.lat.toString()},${geo?.lon.toString()}"
        val urlpic = "https://maps.googleapis.com/maps/api/staticmap?center=$cords&markers=$cords&language=he&region=IL&zoom=16&size=640x400"

        val bigPictureStyle = NotificationCompat.BigPictureStyle()
        val summaryText = context.getString(R.string.notif_event_title, case, carModel)
        bigPictureStyle.setBigContentTitle(address)
        bigPictureStyle.setSummaryText(summaryText)
        try {
            bigPictureStyle.bigPicture(Picasso.with(context).load(urlpic).get())
        } catch (e: Exception) {
            // problem with picture
        }
        return Triple(address, bigPictureStyle, summaryText)
    }

    fun notifyNow(){
        NotificationManagerCompat.from(context)
                .notify(event.hashCode(), notification)
    }
}