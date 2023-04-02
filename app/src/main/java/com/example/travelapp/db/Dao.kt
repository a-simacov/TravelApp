package com.example.travelapp.db

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@androidx.room.Dao
interface Dao {
    @Insert
    suspend fun insertPlace(places: Places)
    @Insert
    suspend fun insertPlaces(places: List<Places>)
    @Delete
    suspend fun deletePlace(places: Places)
    @Query("SELECT * FROM places")
    fun getAllPlaces(): LiveData<List<Places>>
    @Query("DELETE FROM places")
    suspend fun deletePlaces()

    @Insert
    suspend fun insertTicket(ticket: Ticket)
    @Insert
    suspend fun insertTickets(tickets: List<Ticket>)
    @Delete
    suspend fun deleteTicket(ticket: Ticket)
    @Query("SELECT * FROM tickets")
    fun getAllTickets(): LiveData<List<Ticket>>
    @Query("DELETE FROM tickets")
    suspend fun deleteTickets()

    @Query("DELETE FROM tickets WHERE id = :id")
    suspend fun deleteTicketById(id: Int)

    @Query("SELECT * FROM tickets WHERE id = :id")
    suspend fun getTicket(id: Int): Ticket

    @Query("SELECT * FROM places WHERE id = :id")
    suspend fun getPlace(id: Int): Places
}