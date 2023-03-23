package com.example.travelapp.db

import androidx.lifecycle.LiveData

class Repository(private val dao: Dao) {
    val places: LiveData<List<Places>> = dao.getAllPlaces()
    val tickets: LiveData<List<Ticket>> = dao.getAllTickets()

    fun addPlace(place: Places) { dao.insertPlace(place) }

    fun deletePlace(place: Places) { dao.deletePlace(place) }

    fun deletePlaces() { dao.deletePlaces() }

    fun addTicket(ticket: Ticket) { dao.insertTicket(ticket) }

    fun getTicket(id: Int): Ticket { return dao.getTicket(id) }

    fun deleteTicket(ticket: Ticket) { dao.deleteTicket(ticket) }

    fun deleteTickets() { dao.deleteTickets() }

    fun deleteTicketById(id: Int) { dao.deleteTicketById(id) }
}