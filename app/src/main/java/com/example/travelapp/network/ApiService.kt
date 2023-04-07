package com.example.travelapp.network

import com.example.travelapp.db.Places
import retrofit2.http.GET

interface ApiService {
    @GET("download/adventures.json")
    suspend fun getAdventures(): MutableList<Places>

    @GET("download/getUser.json")
    suspend fun getUser(): Map<String, String>
}