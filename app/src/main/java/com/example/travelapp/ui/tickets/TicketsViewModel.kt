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
import com.example.travelapp.network.NetworkRepo
import com.example.travelapp.user.User
import com.example.travelapp.user.UserRepository
import kotlinx.coroutines.*

// используется именно AndroidViewModel, т.к. она может принимать контекст
class TicketsViewModel(application: Application) : AndroidViewModel(application) {

    var tickets = MutableLiveData<List<Ticket>>()
    private val repository: Repository
    val searchText = MutableLiveData("")
    val appUser = MutableLiveData<User>()
    var weather = MutableLiveData<MutableMap<Ticket, CityWeather>>()

    init {
        val dao = Db.getDb(application).getDao()
        repository = Repository(dao)

        val userRepository = UserRepository(application.applicationContext)
        viewModelScope.launch {
            appUser.value = userRepository.getAppUser()
        }

        initTicketsAndWeather(application.applicationContext)
    }

    private fun initTicketsAndWeather(context: Context) {
        viewModelScope.launch {
            val dTickets = withContext(Dispatchers.IO) { repository.getTickets() }
            weather.value = NetworkRepo(context).getCitiesWeather(dTickets)
            tickets.value = dTickets
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
