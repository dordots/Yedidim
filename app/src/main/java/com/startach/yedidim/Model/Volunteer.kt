package com.startach.yedidim.Model

import com.google.gson.annotations.SerializedName

data class Volunteer(
        @SerializedName("AnotherVehicle") val anotherVehicle: String,
        @SerializedName("Area") val area: String,
        @SerializedName("City") val city: String,
        @SerializedName("DateOfBirth") val dateOfBirth: String,
        @SerializedName("DriveCode") val driveCode: String,
        @SerializedName("EmailAddress") val emailAddress: String,
        @SerializedName("Equipment") val equipment: String,
        @SerializedName("FirstName") val firstName: String,
        @SerializedName("IdentityNumber") val identityNumber: String,
        @SerializedName("LastName") val lastName: String,
        @SerializedName("LicenseNumber") val licenseNumber: String,
        @SerializedName("LicenseNumber2") val licenseNumber2: String,
        @SerializedName("MobilePhone") val mobilePhone: String,
        @SerializedName("Occupation") val occupation: String,
        @SerializedName("ProfessionalTrainingCourses") val professionalTrainingCourses: String,
        @SerializedName("StreetAddress") val streetAddress: String,
        @SerializedName("VehicleMake") val vehicleMake: String,
        @SerializedName("VehicleMake2") val vehicleMake2: String,
        @SerializedName("YourVehicle") val yourVehicle: String
)