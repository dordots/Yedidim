package com.startach.yedidim.network

import com.startach.yedidim.Model.Volunteer
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface YedidimApiService {

    @GET("phones/{phoneNum}.json")
    fun getRegistrationState(@Path("phoneNum") phoneNum: String): Single<String?>

    @GET("volunteer/{id}.json")
    fun getVolunteerById(@Path("id") id: String): Single<Volunteer>

    /**
     * Note: startPhoneNum/endPhoneNum must be enclosed with quotation marks, i.e. "0501234567"
     */
    @GET("volunteer.json?orderBy=\"MobilePhone\"")
    fun getVolunteerByPhoneNum(@Query("startAt") startPhoneNum: String, @Query("endAt") endPhoneNum: String): Single<Map<String, Volunteer>>

    @PATCH("volunteer/{id}.json")
    fun updateVolunteerFcmInstanceID(@Path("id") id: String, @Body volunteer: Volunteer): Completable
}
