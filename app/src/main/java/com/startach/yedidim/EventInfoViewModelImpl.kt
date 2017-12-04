package com.startach.yedidim

import com.startach.yedidim.Model.Event
import io.reactivex.Observable

class EventInfoViewModelImpl : EventInfoViewModel {

    lateinit var event: Event

    override fun bindViewModel(event: Event) {
        this.event = event
    }

    override fun eventLoadedObservable(): Observable<Event> {
        return Observable.just(event)
    }
}