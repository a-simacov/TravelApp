package com.example.travelapp.ui.tickets

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.travelapp.db.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// используется именно AndroidViewModel, т.к. она может принимать контекст
class TicketsViewModel(application: Application) : AndroidViewModel(application) {

    var tickets: LiveData<List<Ticket>>
    private val repository: Repository
    val searchText = MutableLiveData("")

    init {
        val dao = Db.getDb(application).getDao()
        repository = Repository(dao)
        tickets = repository.tickets
    }

    fun delete(ticket: Ticket) {
        CoroutineScope(Dispatchers.Unconfined).launch {
            repository.deleteTicket(ticket)
        }
    }

    fun clear() {
        CoroutineScope(Dispatchers.Unconfined).launch {
            repository.deleteTickets()
        }
    }

    fun add(ticket: Ticket) {
        CoroutineScope(Dispatchers.Unconfined).launch {
        repository.addTicket(ticket)
        }
    }

    fun deleteById(id: Int) {
        CoroutineScope(Dispatchers.Unconfined).launch {
            repository.deleteTicketById(id)
        }
    }

}
