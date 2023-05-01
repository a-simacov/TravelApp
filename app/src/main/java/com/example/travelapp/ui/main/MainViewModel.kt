package com.example.travelapp.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.travelapp.App
import com.example.travelapp.user.User

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val appUser = MutableLiveData<User>()

    init {
        appUser.value = (application as App).getUser()
    }

}