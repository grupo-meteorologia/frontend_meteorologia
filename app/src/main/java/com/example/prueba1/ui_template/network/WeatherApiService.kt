package com.example.prueba1.ui_template.network

import com.example.prueba1.ui_template.data.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("forecast")
    suspend fun getCurrentWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current_weather") currentWeather: Boolean = true,
        @Query("hourly") hourly: String = "temperature_2m,weathercode", // Solicita temperatura y weathercode por hora
        @Query("daily") daily: String = "weathercode,temperature_2m_max,temperature_2m_min", // Solicita weathercode y temperaturas max/min diarias
        @Query("timezone") timezone: String = "auto" // Para asegurar que la hora sea local
    ): Response<WeatherResponse>
}