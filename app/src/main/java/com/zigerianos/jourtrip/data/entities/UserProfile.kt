package com.zigerianos.jourtrip.data.entities

import com.google.gson.annotations.SerializedName

data class UserProfile(
    val fullname: String?,
    val username: String?,
    val email: String?,
    val photo: String?,
    val following: Int?,
    val followers: Int?,
    @SerializedName("creation_date")
    val creationDate: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    val comments: List<Comment>?,
    @SerializedName("commentsCount")
    val commentsCount: Int?
)