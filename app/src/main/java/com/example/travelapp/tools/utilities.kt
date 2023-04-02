package com.example.travelapp.tools

import android.app.SearchManager
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

fun openSearch(context: Context, text: String?) {
    if (text.isNullOrBlank()) return

    val searchIntent = Intent(Intent.ACTION_WEB_SEARCH)
    searchIntent.putExtra(SearchManager.QUERY, text)
    context.startActivity(searchIntent)
}