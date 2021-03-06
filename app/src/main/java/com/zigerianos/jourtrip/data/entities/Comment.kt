package com.zigerianos.jourtrip.data.entities

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("_id")
    val id: String?,
    val user: User?,
    val location: Location?,
    val description: String?,
    @SerializedName("creation_date")
    val creationDate: String?
)