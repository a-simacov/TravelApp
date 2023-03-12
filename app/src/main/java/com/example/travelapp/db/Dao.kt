package com.example.travelapp.db

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@androidx.room.Dao
interface Dao {
    @Insert
    fun insertPlace(places: Places)
    @Delete
    fun deletePlace(places: Places)
    @Query("SELECT * FROM places")
    fun getAllPlaces(): List<Places>
    @Query("DELETE FROM places")
    fun deletePlaces()
}