package com.startach.yedidim

import com.startach.yedidim.Model.Event
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single


interface EventInfoViewModel {

    fun bindViewModel(event: Event)

    fun eventLoadedObservable(): Observable<Event>

    fun takeEvent(): Single<Boolean>

    fun ignoreEvent(): Completable
}