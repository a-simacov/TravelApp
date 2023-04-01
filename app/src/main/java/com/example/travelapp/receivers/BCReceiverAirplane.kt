package com.example.travelapp.receivers

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class BCReceiverAirplane : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val state = intent.getBooleanExtra("state", false)
        if (!state) {
            val prefs = context.applicationContext.getSharedPreferences("travel_data", AppCompatActivity.MODE_PRIVATE)
            prefs.also {
                val count = it.getInt("flights_count", 0) + 1
                it.edit().putInt("flights_count", count).apply()
                notifyFlightsCountUpdated(context, count)
            }
        }
    }

    private fun notifyFlightsCountUpdated(context: Context, count: Int) {
        Intent().also { intent ->
            intent.action = "com.example.travelapp.flightsCountUpdated"
            intent.putExtra("flights_count", count + 1)
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
        }
    }
}