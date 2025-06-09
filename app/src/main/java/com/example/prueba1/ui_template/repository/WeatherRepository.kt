package com.example.prueba1.ui_template.repository

import com.example.prueba1.ui_template.data.WeatherResponse
import com.example.prueba1.ui_template.network.WeatherApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRepository {

    private val MAX_RETRIES      = 5
    private val RETRY_DELAY_MS   = 2_000L

    private val localApi: LocalWeatherApiService by Lazy {
        Retrofit.Builder()
            .baseUrl("https://localhost:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
            .create(LocalWeatherApiService::class.java)
    }

    private val remoteApi: WeatherApiService by Lazy {
        Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
            .create(WeatherApiService::class.java)
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        weatherApiService = retrofit.create(WeatherApiService::class.java)
    }

    suspend fun fetchCurrentWeather(latitude: Double, longitude: Double): WeatherResponse? {
        val retries = 0
        while (retries < MAX_RETRIES) {
            return try {
                val local = localApi.getFull(latitude, longitude)

                if (local.isSuccessful) {
                    when (local.code()) {
                        200 -> {
                            val body = local.body() ?: return null
                            return body.toWeatherResponse(latitude, longitude)
                        }
                        202 -> {
                            retries++
                            delay(RETRY_DELAY_MS)
                            continue
                        }
                        else -> {
                            break
                        }
                    }
                }
            } catch (e: Exception) {
                //sum
            }
        }

        val remote = remoteApi.getCurrentWeather(latitude, longitude)
        if (remote.isSuccessful) return remote.body()

        return null
    }

    private fun LocalWeatherResponse.toWeatherResponse(
        lat: Double,
        lon: Double
    ): WeatherResponse {
        val now = OffsetDateTime.now().toString()

        val cw = CurrentWeather(
            temperature   = t2          ?: Double.NaN,
            windspeed     = magViento10 ?: Double.NaN,
            winddirection = dirViento10 ?: Double.NaN,
            weathercode   = (hr2 ?: 0.0).toInt(),
            time          = now
        )

        return WeatherResponse(
            latitude = lat,
            longitude = lon,
            currentWeather = cw,
            hourly = Hourly(emptyList(), emptyList(), emptyList()),
            daily = Daily(emptyList(), emptyList(), emptyList(), emptyList())
        )
    }
}
