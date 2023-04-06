package com.example.travelapp.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Repository(private val dao: Dao) {
    val places = MutableLiveData<List<Places>>(emptyList())
    val tickets = MutableLiveData<List<Ticket>>(emptyList())

    suspend fun addPlace(place: Places) { dao.insertPlace(place) }

    suspend fun addPlaces(places: List<Places>) { dao.insertPlaces(places) }

    suspend fun getPlace(id: Int): Places { return dao.getPlace(id) }

    suspend fun getAllPlaces(): List<Places> { return dao.getAllPlaces() }

    suspend fun deletePlace(place: Places) { dao.deletePlace(place) }

    suspend fun deletePlaces() { dao.deletePlaces() }

    suspend fun addTicket(ticket: Ticket) { dao.insertTicket(ticket) }

    suspend fun addTickets(tickets: List<Ticket>) { dao.insertTickets(tickets) }

    suspend fun getTicket(id: Int): Ticket { return dao.getTicket(id) }

    suspend fun getAllTickets(): List<Ticket> { return dao.getAllTickets() }

    suspend fun deleteTicket(ticket: Ticket) { dao.deleteTicket(ticket) }

    suspend fun deleteTickets() { dao.deleteTickets() }

    suspend fun deleteTicketById(id: Int) { dao.deleteTicketById(id) }
}