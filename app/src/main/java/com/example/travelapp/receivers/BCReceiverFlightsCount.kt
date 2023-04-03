package com.example.travelapp.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.example.travelapp.tools.Constants

class BCReceiverFlightsCount : BroadcastReceiver() {
    val flightsCount = MutableLiveData(0)

    override fun onReceive(context: Context, intent: Intent) {
        flightsCount.value = intent.getIntExtra(Constants.FLIGHTS_COUNT_KEY, 0)
    }
}