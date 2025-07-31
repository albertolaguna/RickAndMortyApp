package com.solera.rickandmortyapp

import retrofit2.Call
import retrofit2.http.GET

interface RickAndMortyService {
    @GET("character")
    fun getCharacters(): Call<CharacterResponse>
}