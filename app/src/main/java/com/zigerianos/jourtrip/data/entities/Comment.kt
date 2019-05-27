package com.zigerianos.jourtrip.data.entities

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("_id")
    val id: String?,
    @SerializedName("user")
    val userId: String?, // TODO: Tiene que recibir user
    val location: Location?,
    val description: String?,
    @SerializedName("creationDate")
    val creationDate: String?
)