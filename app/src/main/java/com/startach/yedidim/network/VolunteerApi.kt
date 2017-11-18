package com.startach.yedidim.network

import com.startach.yedidim.Model.Volunteer
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit


class VolunteerApi(retrofit: Retrofit, retrofitCloudFunctions: Retrofit) {

    private val yedidimApi = retrofit.create(YedidimApiService::class.java)
    private val yedidimCloudFunctionsApi = retrofitCloudFunctions.create(YedidimCloudFunctionsApiService::class.java)

    fun updateInstanceId(volunteerId: String, instanceId: String): Completable {
        val volunteer = Volunteer(fcmToken = instanceId, deviceType = "android")
        return yedidimApi.updateVolunteerFcmInstanceID(volunteerId, volunteer)
                .subscribeOn(Schedulers.io())
    }

    fun volunteerByPhone(phoneNumber: String): Single<Volunteer> {
        val normalizedPhoneNum = phoneNumber.replace("+972", "0")
        val numWithQuotationMarks = "\"$normalizedPhoneNum\""
        return yedidimApi.getVolunteerByPhoneNum(numWithQuotationMarks, numWithQuotationMarks)
                .map {
                    if (it.values.isEmpty()) {
                        throw Exception("No user found for given phone num = $normalizedPhoneNum")
                    } else {
                        it.entries.first().value
                    }
                }
                .subscribeOn(Schedulers.io())
    }

    fun updateVolunteerLocation(volunteerId: String, latitude: Double, longitude: Double): Completable {
        val request = LocationUpdateRequest(volunteerId, latitude, longitude)

        return yedidimCloudFunctionsApi.updateLocation(request)
                .flatMapCompletable { response ->
                    if ("OK!" == response) {
                        return@flatMapCompletable Completable.complete()
                    } else Completable.error(Exception("cannot update location: $response"))
                }
                .subscribeOn(Schedulers.io())
    }
}