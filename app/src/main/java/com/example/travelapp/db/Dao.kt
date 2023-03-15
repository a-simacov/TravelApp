package com.example.travelapp.db

import androidx.lifecycle.LiveData
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
    fun getAllPlaces(): LiveData<List<Places>>
    @Query("DELETE FROM places")
    fun deletePlaces()

    @Insert
    fun insertTicket(ticket: Ticket)
    @Delete
    fun deleteTicket(ticket: Ticket)
    @Query("SELECT * FROM tickets")
    fun getAllTickets(): LiveData<List<Ticket>>
    @Query("DELETE FROM tickets")
    fun deleteTickets()
}