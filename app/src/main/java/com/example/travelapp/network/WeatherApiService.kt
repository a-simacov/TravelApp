package com.example.travelapp.network

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("current.json")
    suspend fun getCityWeather(@Query("q") q: String): CityWeather

    @GET("forecast.json")
    suspend fun getCityWeather(@Query("q") q: String, @Query("dt") dt: String): CityWeather

}