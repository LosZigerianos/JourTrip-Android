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
    @SerializedName("comments_count")
    val commentsCount: Int?,
    @SerializedName("is_following_user")
    val isFollowingUser: Boolean?
)