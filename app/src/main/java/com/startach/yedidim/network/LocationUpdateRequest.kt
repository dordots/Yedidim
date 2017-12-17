package com.startach.yedidim.network

import com.google.gson.annotations.SerializedName

data class LocationUpdateRequest(
        @SerializedName("id") val volunteerId: String,
        @SerializedName("latitude") val latitude: Double,
        @SerializedName("longtitude") val longitude: Double
)