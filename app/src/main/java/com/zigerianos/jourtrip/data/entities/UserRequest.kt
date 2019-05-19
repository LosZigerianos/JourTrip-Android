package com.zigerianos.jourtrip.data.entities

data class UserRequest(
    val username: String? = null,
    val email: String,
    val password: String = ""
)