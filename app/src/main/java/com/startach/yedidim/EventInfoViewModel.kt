package com.startach.yedidim

import com.startach.yedidim.Model.Event
import io.reactivex.Observable


interface EventInfoViewModel {

    fun eventLoadedObservable(): Observable<Event>

    fun bindViewModel(event: Event)
}