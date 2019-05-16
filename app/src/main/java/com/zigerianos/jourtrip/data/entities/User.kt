package com.zigerianos.jourtrip.data.entities

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("_id")
    var id: String = "",
    var username: String = "",
    var name: String = "",
    var email: String = "",
    var accessToken: String = "",
    var creationDate: String = "",
    var updated_at: String = "",
    var provider: String = "",
    var following: List<String> = emptyList(),
    var followers: List<String> = emptyList()
)