package com.startach.yedidim

import com.startach.yedidim.Model.Event
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by yb34982 on 30/12/2017.
 */
interface EventInfoServiceViewModel {
    fun bindViewModel(event: Event)
    fun takeEvent(): Single<Boolean>
    fun ignoreEvent(): Completable
}