package com.startach.yedidim.network

import com.startach.yedidim.Model.Event
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface YedidimCloudFunctionsApiService {

    @POST("/setLocation")
    fun updateLocation(@Body locationRequest: LocationUpdateRequest): Single<String>

    @POST("/takeEvent")
    fun takeEvent(@Body request: TakeEventRequest): Single<Response<String>>

    @GET("/getOpenedEvents")
    fun getEventsList() : Single<Set<Event>>
}