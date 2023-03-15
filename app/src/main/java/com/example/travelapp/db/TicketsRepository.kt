package com.example.travelapp.db

import androidx.lifecycle.LiveData

class TicketsRepository(private val dao: Dao) {
    val tickets: LiveData<List<Ticket>> = dao.getAllTickets()

    fun delete(ticket: Ticket) { dao.deleteTicket(ticket) }

    fun deleteTickets() { dao.deleteTickets() }

    fun addTicket(ticket: Ticket) { dao.insertTicket(ticket) }
}