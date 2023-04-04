package com.example.travelapp.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    var userName = MutableLiveData<String>()

    init {
        userName.value = "in process..."
        updateUserName()
    }

    private fun updateUserName() {
        CoroutineScope(Dispatchers.IO).launch {
            delay(2000L)
            userName.postValue("User NAME")
        }
    }
}