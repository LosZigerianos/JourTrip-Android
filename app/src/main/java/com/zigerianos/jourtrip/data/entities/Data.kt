package com.zigerianos.jourtrip.data.entities

data class Data<D>(var data: D, val success: Boolean, val message: String = "")

data class DataWithMeta<D, M>(var data: D, var metadata: M, val success: Boolean, val message: String = "")

data class Token<D>(var token: D, val success: Boolean)
