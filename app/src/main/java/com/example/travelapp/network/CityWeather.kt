package com.example.travelapp.network

data class CityWeather (
    val location: Location,
    val current: Current
)

data class Location(val name: String)

data class Current(val temp_c: Double, val condition: Condition)

data class Condition(val text: String, val icon: String)