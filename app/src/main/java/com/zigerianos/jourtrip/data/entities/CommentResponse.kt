package com.zigerianos.jourtrip.data.entities

import com.google.gson.annotations.SerializedName

class CommentResponse(
    @SerializedName("_id")
    val id: String?,
    val user: User?,
    val location: Location?,
    val description: String?,
    @SerializedName("creation_date")
    val creationDate: String?
)