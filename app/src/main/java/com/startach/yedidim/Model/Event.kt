package com.startach.yedidim.Model

import android.content.res.Resources
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.startach.yedidim.R
import com.startach.yedidim.utils.empty
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Event(
		@SerializedName("details") val details: Details? = Details(),
		@SerializedName("dispatcher") val dispatcher: String? = "", //003
		@SerializedName("key") val key: String? = "", //-KwEbiayp9GQvqFESoFl
		@SerializedName("lastMessage") val lastMessage: String? = "", //confirm_request
		@SerializedName("psid") val psid: String? = "", //1895445410472195
		@SerializedName("source") val source: String? = "", //fb-bot
		@SerializedName("status") val status: String? = "", //completed
		@SerializedName("timestamp") val timestamp: Long? = 0, //1507878934459
		@SerializedName("assignedTo") val assignedTo: String? = null
): Parcelable {
    constructor(caseIn: Int?) : this(Details(case = caseIn))
    constructor(caseIn: Int?, lat: Double?, lon: Double?) : this(Details(case = caseIn,geo = Geo(lat,lon)))
}

@Parcelize
data class Details(
		@SerializedName("address") val address: String? = "", //אבן גבירול 34 תל אביב
		@SerializedName("caller name") val callerName: String? = "", //דני דין
		@SerializedName("car type") val carType: String? = "", //מאזדה 5
		@SerializedName("case") val case: Int? = 0, //4
		@SerializedName("city") val city: String? = "", //תל אביב
		@SerializedName("full_address") val fullAddress: String? = "", //בבלי 1, תל אביב יפו, ישראל
		@SerializedName("geo") val geo: Geo? = Geo(),
		@SerializedName("more") val more: String? = "", //נתקעתי בלי דלק
		@SerializedName("phone number") val phoneNumber: String? = "" //0542269106
) : Parcelable

@Parcelize
data class Geo(
		@SerializedName("lat") val lat: Double? = 0.0, //32.09347220000001
		@SerializedName("lon") val lon: Double? = 0.0 //34.8000213
) : Parcelable



fun Event.displayableCase(resources: Resources): String? {
    var case: String? = String.empty
    val events = resources.getStringArray(R.array.event_cases)
    val eventCase = this.details?.case
    eventCase?.let {
        if (events.indices.contains(eventCase)) {
            case = events[eventCase]
        }
    }
    return case
}


