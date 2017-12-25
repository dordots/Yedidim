package com.startach.yedidim

import com.startach.yedidim.Model.Event
import com.startach.yedidim.entities.notification.EventNotificationEntity
import com.startach.yedidim.network.EventApi
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class EventInfoViewModelImpl(private val eventApi: EventApi, private val eventNotificationEntity: EventNotificationEntity) : EventInfoViewModel {
    lateinit var event: Event

    var eventTaken: Boolean = false
    override fun bindViewModel(event: Event) {
        this.event = event
    }

    override fun eventLoadedObservable(): Observable<Event> {
        return Observable.just(event)
    }

    override fun takeEvent(): Single<Boolean> {
        return eventApi.takeEvent(event.key.orEmpty())
    }

    override fun ignoreEvent(): Completable {
        return Completable.fromAction { eventNotificationEntity.dismissNotification(event) }
    }

    override fun closeEvent(): Completable {
        return eventApi.closeEvent(event.key.orEmpty())
                .doOnComplete { eventNotificationEntity.dismissNotification(event) }
    }

    override fun cancelEvent(): Completable {
        return eventApi.cancelEvent(event.key.orEmpty())
    }
}
