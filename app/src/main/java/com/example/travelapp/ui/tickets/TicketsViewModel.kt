package com.example.travelapp.ui.tickets

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.travelapp.db.*
import kotlin.concurrent.thread

class TicketsViewModel(application: Application) : AndroidViewModel(application) {

    var tickets: LiveData<List<Ticket>>
    private val repository: Repository

    init {
        val dao = Db.getDb(application).getDao()
        repository = Repository(dao)
        tickets = repository.tickets
    }

    fun delete(ticket: Ticket) { thread { repository.deleteTicket(ticket) } }

    fun deleteTickets() { thread { repository.deleteTickets() } }

    fun add(ticket: Ticket) { thread { repository.addTicket(ticket) } }

    fun deleteTicketById(id: Int) { thread { repository.deleteTicketById(id) } }
}
