package com.example.travelapp.ui.tickets

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.travelapp.db.Db
import com.example.travelapp.db.Repository
import com.example.travelapp.db.Ticket
import com.example.travelapp.network.CityWeather
import com.example.travelapp.network.WeatherRFClient
import com.example.travelapp.tools.getMinDate
import com.example.travelapp.tools.getUserNamePrefs
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

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
        userName.value = getUserNamePrefs(application.applicationContext)
        updateWeather(application.applicationContext)
    }

    private fun updateWeather(context: Context) {
        viewModelScope.launch {
            val dTickets = withContext(Dispatchers.IO) { repository.getTickets() }
            val citiesWeather = mutableMapOf<Ticket, CityWeather>()
            try {
                dTickets.forEach { ticket ->
                    val dateWeather = getMinDate(ticket.arrivalDate)
                    citiesWeather[ticket] = WeatherRFClient.retroifitService
                        .getCityWeather(ticket.cityTo, dateWeather)
                }
                weather.value = citiesWeather
            } catch (e: java.lang.Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }
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
        val map = mutableMapOf<CoroutineDispatcher, Long>(
            Dispatchers.Main to 0,
            Dispatchers.Unconfined to 0,
            Dispatchers.IO to 0,
            Dispatchers.Default to 0
        )
        map.forEach { (disp, _) ->
            map[disp] = measureTimeMillis {
                viewModelScope.launch {
                    repository.addTicket(ticket)
                }
            }
            Log.i("DispatchersTime", "$disp time: ${map[disp]}")
        }

        val winner = map.minBy {it.value}
        Log.i( "DispatchersTime", "Winner is $winner")
    }

    fun deleteById(id: Int) {
        viewModelScope.launch {
            repository.deleteTicketById(id)
        }
    }

}
