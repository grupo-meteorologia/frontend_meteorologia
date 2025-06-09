package com.example.prueba1.ui_template.data

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val latitude: Double,
    val longitude: Double,
    @SerializedName("current_weather")
    val currentWeather: CurrentWeather,
    val hourly: Hourly, // Añadimos el pronóstico por hora
    val daily: Daily // Añadimos el pronóstico diario
)

data class CurrentWeather(
    val temperature: Double,
    val windspeed: Double,
    val winddirection: Double,
    val weathercode: Int,
    val time: String
)

data class Hourly(
    val time: List<String>,
    @SerializedName("temperature_2m")
    val temperature2m: List<Double>,
    @SerializedName("weathercode")
    val weathercode: List<Int>
)

data class Daily(
    val time: List<String>,
    @SerializedName("weathercode")
    val weathercode: List<Int>,
    @SerializedName("temperature_2m_max")
    val temperature2mMax: List<Double>,
    @SerializedName("temperature_2m_min")
    val temperature2mMin: List<Double>
)