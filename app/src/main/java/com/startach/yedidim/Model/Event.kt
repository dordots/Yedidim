package com.startach.yedidim.Model

import android.arch.persistence.room.*
import android.content.res.Resources
import android.os.Parcelable
import android.support.annotation.NonNull
import com.google.gson.annotations.SerializedName
import com.startach.yedidim.R
import com.startach.yedidim.utils.empty
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Event(
		@Embedded @SerializedName("details") var details: Details? = Details(),
		@SerializedName("dispatcher") var dispatcher: String? = "", //003
		@PrimaryKey @NonNull @SerializedName("key") var key: String = "", //-KwEbiayp9GQvqFESoFl
		@SerializedName("lastMessage") var lastMessage: String? = "", //confirm_request
		@SerializedName("psid") var psid: String? = "", //1895445410472195
		@SerializedName("source") var source: String? = "", //fb-bot
		@SerializedName("status") var status: String? = "", //completed
		@SerializedName("timestamp") var timestamp: Long? = 0, //1507878934459
		@SerializedName("assignedTo") var assignedTo: String? = null
): Parcelable {
	@Ignore constructor() : this(0)
    @Ignore constructor(caseIn: Int?) : this(Details(case = caseIn))
	@Ignore constructor(caseIn: Int?, lat: Double?, lon: Double?) : this(Details(case = caseIn,geo = Geo(lat,lon)))
	@Ignore constructor(keyIn : String , caseIn: Int?, lat: Double?, lon: Double?) : this(key = keyIn, details = Details(case = caseIn,geo = Geo(lat,lon)))
	@Ignore constructor(key: String,status: String?) : this(details = null,dispatcher = null,key = key,lastMessage = null,psid = null,source = null,status = status,timestamp = null,assignedTo = null)
	@Ignore constructor(key: String,status: String?,assignedTo: String?) : this(details = null,dispatcher = null,key = key,lastMessage = null,psid = null,source = null,status = status,timestamp = null,assignedTo = assignedTo)
}

@Parcelize
data class Details(
		@SerializedName("address") var address: String? = "", //אבן גבירול 34 תל אביב
		@SerializedName("caller name") var callerName: String? = "", //דני דין
		@SerializedName("car type") var carType: String? = "", //מאזדה 5
		@SerializedName("case") var case: Int? = 0, //4
		@SerializedName("city") var city: String? = "", //תל אביב
		@SerializedName("full_address") var fullAddress: String? = "", //בבלי 1, תל אביב יפו, ישראל
		@Embedded @SerializedName("geo") var geo: Geo? = Geo(Double.NaN, Double.NaN),
		@SerializedName("more") var more: String? = "", //נתקעתי בלי דלק
		@SerializedName("phone number") var phoneNumber: String? = "" //0542269106
) : Parcelable

@Parcelize
data class Geo(
		@SerializedName("lat") var lat: Double?, //32.09347220000001
		@SerializedName("lon") var lon: Double? //34.8000213
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


