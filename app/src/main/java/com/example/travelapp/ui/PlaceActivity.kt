package com.example.travelapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.travelapp.R

class PlaceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place)
        fillPlaceData(intent)
    }

    private fun fillPlaceData(intent: Intent) {
        val textName = findViewById<TextView>(R.id.textName)
        val textInfo = findViewById<TextView>(R.id.textInfo2)
        val textDetail = findViewById<TextView>(R.id.textDetail)
        with (intent) {
            textName?.text = getStringExtra("place_name")
            textInfo?.text = getStringExtra("place_info")
            textDetail?.text = getStringExtra("place_textDetail")
        }
    }
}