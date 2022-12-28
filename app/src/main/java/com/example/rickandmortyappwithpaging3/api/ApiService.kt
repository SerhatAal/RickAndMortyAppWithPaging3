package com.example.rickandmortyappwithpaging3.api

import com.example.rickandmortyappwithpaging3.model.CharacterResponse
import com.example.rickandmortyappwithpaging3.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(Constants.END_POINT)
    suspend fun getAllCharacters(
        @Query(value = "page") page: Int
    ): Response<CharacterResponse>
}