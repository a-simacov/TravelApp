package com.example.travelapp.ui.tickets

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.travelapp.db.*
import com.example.travelapp.tools.getUserNamePrefs
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

// используется именно AndroidViewModel, т.к. она может принимать контекст
class TicketsViewModel(application: Application) : AndroidViewModel(application) {

    var tickets: LiveData<List<Ticket>>
    private val repository: Repository
    val searchText = MutableLiveData("")
    var userName = MutableLiveData<String>()

    init {
        val dao = Db.getDb(application).getDao()
        repository = Repository(dao)
        tickets = repository.tickets
        userName.value = getUserNamePrefs(application.applicationContext)
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
        val map = mutableMapOf<CoroutineDispatcher, Long>(
            Dispatchers.Main to 0,
            Dispatchers.Unconfined to 0,
            Dispatchers.IO to 0,
            Dispatchers.Default to 0
        )
        map.forEach { (disp, _) ->
            map[disp] = measureTimeMillis {
                CoroutineScope(disp).launch {
                    repository.addTicket(ticket)
                }
            }
            Log.i("DispatchersTime", "$disp time: ${map[disp]}")
        }

        val winner = map.minBy {it.value}
        Log.i( "DispatchersTime", "Winner is $winner")
    }

    fun deleteById(id: Int) {
        CoroutineScope(Dispatchers.Unconfined).launch {
            repository.deleteTicketById(id)
        }
    }

}
