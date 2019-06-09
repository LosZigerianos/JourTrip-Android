package com.zigerianos.jourtrip.data.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location(
    @SerializedName("_id")
    val id: String?,
    val name: String?,
    val address: String?,
    @SerializedName("postal_code")
    val postalCode: String?,
    val city: String?,
    val state: String?,
    val country: String?,
    @SerializedName("formatted_address")
    val formattedAddress: String?,
    val photos: List<String>? = null,
    val tags: List<String>? = null,
    val cc: String? = null
) : Parcelable