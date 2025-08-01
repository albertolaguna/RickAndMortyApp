package com.solera.rickandmortyapp

data class EpisodeResponse(
    val result: List<Episode>
)

data class Episode(
    val id: Int,
    val name: String,
    val airDate: String
)
