package com.solera.rickandmortyapp

data class CharacterResponse(
    val info: Info,
    val results: List<Character>
)

data class Info(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)

data class Character(
    val id: Int,
    val name: String,
    val species: String,
    val image: String,
    val origin: Origin
)

data class Origin(
    val name: String,
    val url: String,
    val status: String,
    val gender: String
)
