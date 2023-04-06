package com.example.travelapp.ui.tickets

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.travelapp.db.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

// используется именно AndroidViewModel, т.к. она может принимать контекст
class TicketsViewModel(application: Application) : AndroidViewModel(application) {

    var tickets = MutableLiveData<List<Ticket>>(emptyList())
    val searchText = MutableLiveData("")
    private var repository: Repository

    init {
        val dao = Db.getDb(application).getDao()
        repository = Repository(dao)
        updateAllTickets()
    }

    fun updateAllTickets() {
        CoroutineScope(Dispatchers.IO).launch {
            tickets.postValue(repository.getAllTickets())
        }
    }

    fun delete(ticket: Ticket) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteTicket(ticket)
            updateAllTickets()
        }
        println(tickets.value?.joinToString())//При удалении билета из базы, не удаляется элемент из LD
    }

    fun clear() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteTickets()
        }
        tickets.value = emptyList()
    }

    fun add(ticket: Ticket) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.addTicket(ticket)
        }
    }

    fun deleteById(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteTicketById(id)
        }
    }

}
