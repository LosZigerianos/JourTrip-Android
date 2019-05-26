package com.zigerianos.jourtrip.data.entities


data class PasswordRequest(
    val password: String,
    val newPassword: String,
    val passwordConfirmation: String
)