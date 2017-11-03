package com.startach.yedidim.network

import com.startach.yedidim.Model.Volunteer
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit


class VolunteerApi(val retrofit: Retrofit) {

    val yedidimApi = retrofit.create(YedidimApiService::class.java)

    fun updateInstanceId(volunteerId: String, instanceId: String): Completable {
        val volunteer = Volunteer(fcmToken = instanceId)
        return yedidimApi.updateVolunteerFcmInstanceID(volunteerId, volunteer)
                .subscribeOn(Schedulers.io())
    }
}