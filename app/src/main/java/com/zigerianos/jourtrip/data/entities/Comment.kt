package com.zigerianos.jourtrip.data.entities

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("_id")
    val id: String?,
    val user: String?, // TODO: User?
    val location: Location?,
    val description: String?,
    @SerializedName("creationDate")
    val creationDate: String?
)