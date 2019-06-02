package com.zigerianos.jourtrip.domain

enum class ServiceError(val error: String) {
    TOKEN_EXPIRED("jwt expired"),
    CREDENTIALS_INVALID("Invalid credentials"),
    UNAUTHORIZED("invalid signature"),
    NOT_FOUND("Not Found"), // No encuentra en Base de datos
    UNKNOWN("");

    companion object {
        fun getServiceError(value: String): ServiceError = values().find { it.error == value } ?: UNKNOWN
    }
}