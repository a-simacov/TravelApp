package com.example.travelapp.tools

import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager

fun sendLocalBroadcastInfo(context: Context, action: String, info: String) {
    Intent().also {
        it.action = action
        it.putExtra("info", info)
        LocalBroadcastManager.getInstance(context).sendBroadcast(it)
    }
}