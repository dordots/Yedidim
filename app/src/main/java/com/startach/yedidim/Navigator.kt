package com.startach.yedidim

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.startach.yedidim.Model.Event
import timber.log.Timber
import java.util.*


class Navigator(private val activity: Activity) {

    fun openFloatingEvent(event: Event, ownEvent: Boolean) {
        val geo = event.details?.geo
        Timber.d("Navigating to lat : %s, lon : %s", geo?.lat, geo?.lon)
        val uri = String.format(Locale.ENGLISH, "geo:%f,%f", geo?.lat, geo?.lon)
        val intentNav = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                .also {
                    it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
        activity.startActivity(intentNav)

        val intent = Intent(activity, ChatHeadService::class.java)
                .apply {
                    this.putExtra(ChatHeadService.SERVICE_EVENT, event)
                    this.putExtra(ChatHeadService.SERVICE_MY_EVENT, ownEvent)
                }
        activity.startService(intent)
    }

    fun openCaller(event: Event) {
        val phonenumber = event.details?.phoneNumber
        Timber.d("Calling phone number : $phonenumber")
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phonenumber"))
                .also {
                    it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
        activity.startActivity(intent)
    }
}