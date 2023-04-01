package com.example.travelapp.tools

import android.content.Context
import android.content.IntentFilter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.travelapp.receivers.BCReceiverFlightsCount

class FlightsCountUpdater(val context: Context) {

    private val receiverFlightsCount = BCReceiverFlightsCount()
    private val prefs = context.getSharedPreferences("travel_data", AppCompatActivity.MODE_PRIVATE)

    init {
        LocalBroadcastManager.getInstance(context).also { bcManager ->
            bcManager.registerReceiver(
                receiverFlightsCount,
                IntentFilter("com.example.travelapp.flightsCountUpdated")
            )
        }
    }

    fun start(textView: TextView) {
        receiverFlightsCount.flightsCount.observe(context as LifecycleOwner) {
            val flighstCount = if (it == 0) prefs.getInt("flights_count", 0) else it
            textView.text = "$flighstCount flights"
        }
    }

    fun stop(textView: TextView) {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(receiverFlightsCount)
    }

}