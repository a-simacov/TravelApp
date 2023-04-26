package com.example.travelapp.db

private const val defaultHotelName = "The Ideal Hotel at a Great Price"
private const val defaultHotelCity = "Moscow"

data class Hotel(
    val name: String = defaultHotelName,
    val city: String = defaultHotelCity,
    val imageUrl: String = "",
    val description: String = ""
)
