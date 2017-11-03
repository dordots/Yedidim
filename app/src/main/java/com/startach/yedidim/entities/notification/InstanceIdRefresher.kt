package com.startach.yedidim.entities.notification

import com.startach.yedidim.network.VolunteerApi
import io.reactivex.Completable


class InstanceIdRefresher(volunteerApi: VolunteerApi) {

    fun refreshInstanceId(): Completable {
        return Completable.complete()
    }
}