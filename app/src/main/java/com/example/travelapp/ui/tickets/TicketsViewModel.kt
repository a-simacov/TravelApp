package com.example.travelapp.ui.tickets

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.travelapp.db.Db
import com.example.travelapp.db.Repository
import com.example.travelapp.db.Ticket
import com.example.travelapp.network.CityWeather
import com.example.travelapp.tools.AppUser
import com.example.travelapp.tools.showToast
import kotlinx.coroutines.*

// используется именно AndroidViewModel, т.к. она может принимать контекст
class TicketsViewModel(application: Application) : AndroidViewModel(application) {

    var tickets = MutableLiveData<List<Ticket>>()
    private val repository: Repository
    val searchText = MutableLiveData("")
    var userName = MutableLiveData<String>()
    var weather = MutableLiveData<MutableMap<Ticket, CityWeather>>()

    init {
        val dao = Db.getDb(application).getDao()
        repository = Repository(dao)
        userName.value = AppUser.name
        initTicketsAndWeather(application.applicationContext)
    }

    // Как разбить этот метод на два?
    private fun initTicketsAndWeather(context: Context) {
        viewModelScope.launch {
            val dTickets = withContext(Dispatchers.IO) { repository.getTickets() }
            try {
                weather.value = repository.getCitiesWeather(dTickets)
            } catch (e: java.lang.Exception) {
                showToast(context, e.message)
            }
            tickets.value = dTickets // Почему, если эту строку переместить выше, то погода
            // не будет возвращать результат???
        }
    }

    fun delete(ticket: Ticket) {
        viewModelScope.launch {
            repository.deleteTicket(ticket)
        }
    }

    fun clear() {
        viewModelScope.launch {
            repository.deleteTickets()
        }
    }

    fun add(ticket: Ticket) {
        viewModelScope.launch {
            repository.addTicket(ticket)
        }
    }

    fun deleteById(id: Int) {
        viewModelScope.launch {
            repository.deleteTicketById(id)
        }
    }

}
