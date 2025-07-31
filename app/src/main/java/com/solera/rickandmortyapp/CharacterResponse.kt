package com.solera.rickandmortyapp

data class CharacterResponse(
    val results: List<Character>
)

data class Character(
    val id: Int,
    val name: String,
    val species: String,
    val image: String
)
