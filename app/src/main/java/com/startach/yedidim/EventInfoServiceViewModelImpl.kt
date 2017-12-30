package com.startach.yedidim

import com.startach.yedidim.Model.Event
import com.startach.yedidim.entities.notification.EventNotificationEntity
import com.startach.yedidim.network.EventApi
import io.reactivex.Completable
import io.reactivex.Single

class EventInfoServiceViewModelImpl(private val eventApi: EventApi, private val eventNotificationEntity: EventNotificationEntity) : EventInfoServiceViewModel {
    lateinit var event: Event

    var eventTaken: Boolean = false
    override fun bindViewModel(event: Event) {
        this.event = event
    }

    override fun takeEvent(): Single<Boolean> {
        return eventApi.takeEvent(event.key.orEmpty())
    }

    override fun ignoreEvent(): Completable {
        return Completable.fromAction { eventNotificationEntity.dismissNotification(event) }
    }

}
