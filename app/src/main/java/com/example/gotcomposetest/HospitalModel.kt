package com.example.gotcomposetest

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class HospitalModel(
    @SerializedName("hospitals")
    val list: ArrayList<HospitalDesModel>
) : Serializable

data class HospitalDesModel(
    @SerializedName("name")
    val name: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("tel")
    val tel: String,
    @SerializedName("gps")
    val gps: String,
    val newLocation: LocationsModel,
    var distance: Double? = null
) : Serializable

data class LocationsModel(
    var latitude: Double,
    var longitude: Double
)

data class CurrentLocationModel(
    var latitude: Double,
    var longitude: Double
)