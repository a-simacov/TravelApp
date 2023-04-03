package com.example.travelapp.tools

import android.content.Context
import android.content.IntentFilter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.travelapp.R
import com.example.travelapp.receivers.BCReceiverFlightsCount

class FlightsCountUpdater(val context: Context) {

    private val receiverFlightsCount = BCReceiverFlightsCount()
    private val prefs = context.getSharedPreferences(
        Constants.PREFS_FILE_NAME,
        AppCompatActivity.MODE_PRIVATE
    )

    init {
        LocalBroadcastManager.getInstance(context).also { bcManager ->
            bcManager.registerReceiver(
                receiverFlightsCount,
                IntentFilter(Constants.LACTION_FLIGHT_COUNTS_UPDATED)
            )
        }
    }

    fun start(textView: TextView) {
        receiverFlightsCount.flightsCount.observe(context as LifecycleOwner) {
            val flighstCount = if (it == 0) prefs.getInt(Constants.FLIGHTS_COUNT_KEY, 0) else it
            textView.text = context.getString(R.string.txtFlightsCount, flighstCount.toString())
        }
    }

    fun stop(textView: TextView) {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(receiverFlightsCount)
    }

}