package com.zigerianos.jourtrip.data.entities

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("_id")
    var id: String = "",
    var username: String = "",
    var fullname: String = "",
    var email: String = "",
    @SerializedName("photo")
    var photo: String? = null,
    var accessToken: String = "",
    @SerializedName("creation_date")
    var creationDate: String = "",
    @SerializedName("updated_at")
    var updatedAt: String = "",
    var provider: String = "",
    var following: List<String> = emptyList()
    //var followers: List<String> = emptyList()
)