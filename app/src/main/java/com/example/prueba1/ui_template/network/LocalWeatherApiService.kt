package com.example.prueba1.ui_template.network

import com.example.prueba1.ui_template.data.LocalWeatherResponse
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LocalWeatherApiService {
    @GET("/")
    suspend fun getFull(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Response<LocalWeatherResponse>

    @GET("/")
    suspend fun getSingleVar(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("var") variable: String
    ): Response<JsonObject>
}

