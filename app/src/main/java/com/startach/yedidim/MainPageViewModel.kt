package com.startach.yedidim

import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Created by yb34982 on 17/12/2017.
 */
interface MainPageViewModel {
    fun isActive() : Observable<Boolean>
    fun setActivateState(state : Boolean): Completable
}