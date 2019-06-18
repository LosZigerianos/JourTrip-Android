package com.zigerianos.jourtrip.data.entities


data class PasswordRequest(
    val oldPassword: String,
    val newPassword: String
)