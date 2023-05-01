package com.example.travelapp.network

import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.travelapp.db.Ticket
import com.example.travelapp.tools.Constants
import com.example.travelapp.tools.getMinDate

class NetworkRepo(private val context: Context) {

    suspend fun getCitiesWeather(tickets: List<Ticket>): MutableMap<Ticket, CityWeather> {
        val citiesWeather = mutableMapOf<Ticket, CityWeather>()

        try {
            tickets.forEach { ticket ->
                val dateWeather = getMinDate(ticket.arrivalDate)
                citiesWeather[ticket] = WeatherRFClient.retroifitService
                    .getCityWeather(ticket.cityTo, dateWeather)
            }
        } catch(e: java.lang.Exception) {
            broadcastError(e.message)
        }

        return citiesWeather
    }

    suspend fun getDefaultUserImgUrl(): String {
        return try {
            getDefaultUser().getValue("image")
        } catch (e: java.lang.Exception) {
            broadcastError(e.message)
            ""
        }
    }

    suspend fun getDefaultUser(): Map<String, String> {
        return try {
            RetrofitClient.retroifitService.getUser()
        } catch (e: java.lang.Exception) {
            broadcastError(e.message)
            mapOf("name" to "default", "image" to "")
        }
    }

    private fun broadcastError(msg: String?) {
        Intent().also { intent ->
            intent.action = Constants.LACTION_REPO_ERROR
            intent.putExtra("error_info", msg)
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
        }
    }

}