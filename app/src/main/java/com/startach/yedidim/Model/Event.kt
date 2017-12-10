package com.startach.yedidim.Model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event(
        @SerializedName("details") val details: Details? = null,
        @SerializedName("key") val key: String? = null,
        @SerializedName("lastMessage") val lastMessage: String? = null,
        @SerializedName("psid") val psid: String? = null,
        @SerializedName("source") val source: String? = null,
        @SerializedName("status") val status: String? = null,
        @SerializedName("timestamp") val timestamp: Long? = null
) : Parcelable

@Parcelize
data class Details(
        @SerializedName("address") val address: String? = null,
        @SerializedName("caller name") val callerName: String? = null,
        @SerializedName("car type") val carType: String? = null,
        @SerializedName("case") val case: Int? = null,
        @SerializedName("city") val city: String? = null,
        @SerializedName("full_address") val fullAddress: String? = null,
        @SerializedName("geo") val geo: Geo? = null,
        @SerializedName("more") val more: String? = null,
        @SerializedName("phone number") val phoneNumber: String? = null,
        @SerializedName("street_name") val streetName: String? = null,
        @SerializedName("street_number") val streetNumber: Int? = null
) : Parcelable

@Parcelize
data class Geo(
        @SerializedName("lat") val lat: Double? = null,
        @SerializedName("lon") val lon: Double? = null
) : Parcelable