package com.example.travelapp.db

import androidx.lifecycle.LiveData

class Repository(private val dao: Dao) {
    val places: LiveData<MutableList<Places>> = dao.getAllPlaces()
    val tickets: LiveData<List<Ticket>> = dao.getAllTickets()

    suspend fun addPlace(place: Places) { dao.insertPlace(place) }

    suspend fun addPlaces(places: List<Places>) { dao.insertPlaces(places) }

    suspend fun getPlace(id: Int): Places { return dao.getPlace(id) }

    suspend fun deletePlace(place: Places) { dao.deletePlace(place) }

    suspend fun deletePlaces() { dao.deletePlaces() }

    suspend fun addTicket(ticket: Ticket) { dao.insertTicket(ticket) }

    suspend fun addTickets(tickets: List<Ticket>) { dao.insertTickets(tickets) }

    suspend fun getTicket(id: Int): Ticket { return dao.getTicket(id) }

    suspend fun getAllTickets(): LiveData<List<Ticket>> { return dao.getAllTickets() }

    suspend fun getTickets(): List<Ticket> { return dao.getTickets() }

    suspend fun deleteTicket(ticket: Ticket) { dao.deleteTicket(ticket) }

    suspend fun deleteTickets() { dao.deleteTickets() }

    suspend fun deleteTicketById(id: Int) { dao.deleteTicketById(id) }

}