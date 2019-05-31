package com.zigerianos.jourtrip.data.entities

import com.google.gson.annotations.SerializedName

class CommentResponse(
    @SerializedName("_id")
    val id: String?,
    //val user: String?, // TODO: REPASAR
    //val location: String?,
    val description: String?,
    @SerializedName("creationDate")
    val creationDate: String?
)