package com.zigerianos.jourtrip.data.entities

data class Data<D>(var data: D, val success: Boolean)

data class DataWithMeta<D, M>(var data: D, var meta: M, val success: Boolean)

data class Token<D>(var token: D, val success: Boolean)
