package com.zigerianos.jourtrip.data.entities

data class Location(
    val name: String,
    val address: String,
    val postalCode: String,
    val city: String,
    val state: String,
    val country: String,
    val formattedAddress: String,
    val photos: List<String> = emptyList()
)