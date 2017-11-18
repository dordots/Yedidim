package com.startach.yedidim.network

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST


interface YedidimCloudFunctionsApiService {

    @POST("/setLocation")
    fun updateLocation(@Body locationRequest: LocationUpdateRequest): Single<String>
}