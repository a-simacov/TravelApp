package com.example.travelapp.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData

class BCReceiverFlightsCount : BroadcastReceiver() {
    val flightsCount = MutableLiveData(0)

    override fun onReceive(context: Context, intent: Intent) {
        flightsCount.value = intent.getIntExtra("flights_count", 0)
    }
}