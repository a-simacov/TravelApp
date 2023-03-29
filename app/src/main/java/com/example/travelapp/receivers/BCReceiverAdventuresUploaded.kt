package com.example.travelapp.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class BCReceiverAdventuresUploaded : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val info = intent.getStringExtra("info")!!
        Toast.makeText(context, info, Toast.LENGTH_LONG).show()
    }
}