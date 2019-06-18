package com.zigerianos.jourtrip.data.entities

data class UserRequest(
    val fullname: String? = null,
    val username: String? = null,
    val email: String,
    val password: String = ""
)