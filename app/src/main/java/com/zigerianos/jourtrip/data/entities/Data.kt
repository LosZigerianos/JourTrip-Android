package com.zigerianos.jourtrip.data.entities

data class Data<D>(var data: D)

data class DataWithMeta<D, M>(var data: D, var meta: M)
