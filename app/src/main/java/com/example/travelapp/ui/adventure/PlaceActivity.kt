package com.example.travelapp.ui.adventure

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.travelapp.R
import com.example.travelapp.databinding.ActivityPlaceBinding

class PlaceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place)
        fillPlaceData(intent)
    }

    private fun fillPlaceData(intent: Intent) {
        val binding = ActivityPlaceBinding.inflate(layoutInflater)
        with (intent) {
            binding.textName.text = getStringExtra("place_name")
            binding.textInfo2.text = getStringExtra("place_info")
            binding.textDetail.text = getStringExtra("place_textDetail")
        }
    }
}