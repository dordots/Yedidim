package com.startach.yedidim.repository

import com.startach.yedidim.Model.Event

/**
 * Created by yb34982 on 28/01/2018.
 */

interface EventsRepository {
    val openEvent: Event?
    fun storeOpenEvent(event: Event)
    fun closeOpenEvent(event: Event, newStatus: String)
}
