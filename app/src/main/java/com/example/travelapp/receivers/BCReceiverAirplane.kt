package com.example.travelapp.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.travelapp.tools.Constants

class BCReceiverAirplane : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val state = intent.getBooleanExtra("state", false)
        if (!state) {
            val prefs = context.applicationContext.getSharedPreferences(
                Constants.PREFS_FILE_NAME,
                AppCompatActivity.MODE_PRIVATE
            )
            prefs.also {
                val count = it.getInt(Constants.FLIGHTS_COUNT_KEY, 0) + 1
                it.edit().putInt(Constants.FLIGHTS_COUNT_KEY, count).apply()
                notifyFlightsCountUpdated(context, count)
            }
        }
    }

    private fun notifyFlightsCountUpdated(context: Context, count: Int) {
        Intent().also { intent ->
            intent.action = Constants.LACTION_FLIGHT_COUNTS_UPDATED
            intent.putExtra(Constants.FLIGHTS_COUNT_KEY, count)
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
        }
    }
}