package com.startach.yedidim.Model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event(
        @SerializedName("details") val details: Details,
        @SerializedName("key") val key: String,
        @SerializedName("lastMessage") val lastMessage: String,
        @SerializedName("psid") val psid: String,
        @SerializedName("source") val source: String,
        @SerializedName("status") val status: String,
        @SerializedName("timestamp") val timestamp: Long
) : Parcelable

@Parcelize
data class Details(
        @SerializedName("address") val address: String,
        @SerializedName("caller name") val callerName: String,
        @SerializedName("car type") val carType: String,
        @SerializedName("case") val case: Int,
        @SerializedName("city") val city: String,
        @SerializedName("full_address") val fullAddress: String,
        @SerializedName("geo") val geo: Geo,
        @SerializedName("more") val more: String,
        @SerializedName("phone number") val phoneNumber: String,
        @SerializedName("street_name") val streetName: String,
        @SerializedName("street_number") val streetNumber: Int
) : Parcelable

@Parcelize
data class Geo(
        @SerializedName("lat") val lat: Double,
        @SerializedName("lon") val lon: Double
) : Parcelable