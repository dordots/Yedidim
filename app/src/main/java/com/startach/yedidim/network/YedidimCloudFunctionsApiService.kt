package com.startach.yedidim.network

import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST


interface YedidimCloudFunctionsApiService {

    @POST("/setLocation")
    fun updateLocation(@Body locationRequest: LocationUpdateRequest): Single<String>
}

data class LocationUpdateRequest(
        @SerializedName("id") val volunteerId: String,
        @SerializedName("latitude") val latitude: Double,
        @SerializedName("longtitude") val longitude: Double
)