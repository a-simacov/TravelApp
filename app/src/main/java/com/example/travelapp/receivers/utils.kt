package com.example.travelapp.receivers

import android.content.Context
import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.travelapp.tools.Constants

fun registerBCReceivers(context: Context) {
    context.registerReceiver(
        BCReceiverBluetooth(),
        IntentFilter(Constants.ACTION_BT_STATE_CHANGED),
        android.Manifest.permission.BLUETOOTH,
        null
    )

    context.registerReceiver(
        BCReceiverAirplane(),
        IntentFilter(Constants.ACTION_AIRPLANE_MODE)
    )

    LocalBroadcastManager.getInstance(context).also { bcManager ->
        bcManager.registerReceiver(
            BCReceiverAdventuresUploaded(),
            IntentFilter(Constants.LACTION_ADV_UPLOADED)
        )
        bcManager.registerReceiver(
            BCReceiverTicketsUploaded(),
            IntentFilter(Constants.LACTION_TICKETS_UPLOADED)
        )
    }
}