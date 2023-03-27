package com.example.travelapp.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@androidx.room.Dao
interface Dao {
    @Insert
    fun insertPlace(places: Places)
    @Insert
    fun insertPlaces(places: List<Places>)
    @Delete
    fun deletePlace(places: Places)
    @Query("SELECT * FROM places")
    fun getAllPlaces(): LiveData<List<Places>>
    @Query("DELETE FROM places")
    fun deletePlaces()

    @Insert
    fun insertTicket(ticket: Ticket)
    @Insert
    fun insertTickets(tickets: List<Ticket>)
    @Delete
    fun deleteTicket(ticket: Ticket)
    @Query("SELECT * FROM tickets")
    fun getAllTickets(): List<Ticket>
    @Query("DELETE FROM tickets")
    fun deleteTickets()

    @Query("DELETE FROM tickets WHERE id = :id")
    fun deleteTicketById(id: Int)

    @Query("SELECT * FROM tickets WHERE id = :id")
    fun getTicket(id: Int): Ticket

    @Query("SELECT * FROM places WHERE id = :id")
    fun getPlace(id: Int): Places
}