package com.zigerianos.jourtrip.domain

enum class ServiceError(val error: String) {
    TOKEN_EXPIRED("jwt expired"),
    UNAUTHORIZED("invalid signature"),
    UNKNOWN("");

    companion object {
        fun getServiceError(value: String): ServiceError = values().find { it.error == value } ?: UNKNOWN
    }
}