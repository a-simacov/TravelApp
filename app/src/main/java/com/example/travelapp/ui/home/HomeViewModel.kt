package com.example.travelapp.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.travelapp.tools.AppUser

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    var userName = MutableLiveData<String>()

    init {
        userName.value = AppUser.name
    }

}