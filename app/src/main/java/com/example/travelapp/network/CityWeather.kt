package com.example.travelapp.network

import com.google.gson.JsonObject

data class CityWeather (
    val location: Location,
    val current: Current,
    val forecast: JsonObject
)

data class Location(val name: String)

data class Current(val temp_c: Double, val condition: Condition)

data class Condition(val text: String, val icon: String)