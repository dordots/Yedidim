package com.startach.yedidim

import com.startach.yedidim.Model.Event
import com.startach.yedidim.network.EventApi
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class EventInfoViewModelImpl(private val eventApi: EventApi) : EventInfoViewModel {
    lateinit var event: Event

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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
