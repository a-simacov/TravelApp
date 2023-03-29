package com.example.travelapp.receivers

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class BCReceiverBluetooth : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        //val state = intent.getBooleanExtra("state", false)
        val extraState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
        val state = when (extraState) {
            BluetoothAdapter.STATE_OFF -> "OFF"
            BluetoothAdapter.STATE_TURNING_OFF -> "TURNING_OFF"
            BluetoothAdapter.STATE_ON -> "ON"
            BluetoothAdapter.STATE_TURNING_ON -> "TURNING_ON"
            else -> "UNKNOWN STATE"
        }
        Toast.makeText(context, "Bluetooth state is $state", Toast.LENGTH_LONG).show()
    }

}