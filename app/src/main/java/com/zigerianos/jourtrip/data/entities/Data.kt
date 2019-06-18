package com.zigerianos.jourtrip.data.entities

data class Data<D>(var data: D, val success: Boolean, val message: String = "", val error: String? = null, val count: Int = -1)

data class DataWithMeta<D, M>(var data: D, var metadata: M, val success: Boolean, val message: String = "", val error: String? = null, val count: Int = -1)

data class TokenResponse(val success: Boolean, val error: String? = null)

data class ErrorResponse(val success: Boolean, val message: String? = null, val error: String = "")
