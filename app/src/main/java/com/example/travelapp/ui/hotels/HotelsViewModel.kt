package com.example.travelapp.ui.hotels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelapp.db.Hotel
import com.example.travelapp.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HotelsViewModel(application: Application) : AndroidViewModel(application) {

    private val pHotels = MutableStateFlow(emptyList<Hotel>())
    val hotels: StateFlow<List<Hotel>>
        get() = pHotels

    init {
        viewModelScope.launch {
            try {
                pHotels.value = RetrofitClient.retroifitService.getHotels()
            } catch (e: Exception) {
                Toast.makeText(application.applicationContext, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

}