package com.example.travelapp.network

import com.example.travelapp.db.Hotel
import com.example.travelapp.db.Places
import retrofit2.http.GET

interface ApiService {
    @GET("download/adventures.json")
    suspend fun getAdventures(): MutableList<Places>

    @GET("download/hotels.json")
    suspend fun getHotels(): MutableList<Hotel>

    @GET("download/getUser.json")
    suspend fun getUser(): Map<String, String>
}