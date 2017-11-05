package com.startach.yedidim.Model

import com.google.gson.annotations.SerializedName

data class Volunteer(
        @SerializedName("AnotherVehicle") val anotherVehicle: String? = null,
        @SerializedName("Area") val area: String? = null,
        @SerializedName("City") val city: String? = null,
        @SerializedName("DateOfBirth") val dateOfBirth: String? = null,
        @SerializedName("DriveCode") val driveCode: String? = null,
        @SerializedName("EmailAddress") val emailAddress: String? = null,
        @SerializedName("Equipment") val equipment: String? = null,
        @SerializedName("FirstName") val firstName: String? = null,
        @SerializedName("IdentityNumber") val id: String? = null,
        @SerializedName("LastName") val lastName: String? = null,
        @SerializedName("LicenseNumber") val licenseNumber: String? = null,
        @SerializedName("LicenseNumber2") val licenseNumber2: String? = null,
        @SerializedName("MobilePhone") val mobilePhone: String? = null,
        @SerializedName("Occupation") val occupation: String? = null,
        @SerializedName("ProfessionalTrainingCourses") val professionalTrainingCourses: String? = null,
        @SerializedName("StreetAddress") val streetAddress: String? = null,
        @SerializedName("VehicleMake") val vehicleMake: String? = null,
        @SerializedName("VehicleMake2") val vehicleMake2: String? = null,
        @SerializedName("YourVehicle") val yourVehicle: String? = null,
        @SerializedName("FCMToken") val fcmToken: String? = null
)