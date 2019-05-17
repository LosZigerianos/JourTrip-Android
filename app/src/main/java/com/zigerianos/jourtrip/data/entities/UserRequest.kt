package com.zigerianos.jourtrip.data.entities

data class UserRequest(
    val username: String = "",
    val email: String,
    val password: String = ""
)