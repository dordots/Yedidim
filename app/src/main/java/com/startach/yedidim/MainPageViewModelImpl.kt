package com.startach.yedidim

import com.startach.yedidim.entities.notification.NotificationDeviceIdSyncer
import com.startach.yedidim.entities.usermanagement.UserManager
import io.reactivex.Completable
import io.reactivex.Observable

class MainPageViewModelImpl(private val userManager: UserManager, private val notificationDeviceIdSyncer : NotificationDeviceIdSyncer) : MainPageViewModel {

    override fun isActive(): Observable<Boolean> = userManager.activeStateSubject

    override fun setActivateState(state: Boolean) : Completable {
        return when(state){
            true -> notificationDeviceIdSyncer.syncDeviceID()
            false -> notificationDeviceIdSyncer.resetDeviceID()
        }
    }
}