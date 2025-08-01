package com.solera.rickandmortyapp

data class LocationResponse(
    val result: Location
)

data class Location(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String
)
