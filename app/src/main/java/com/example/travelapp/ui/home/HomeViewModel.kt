package com.example.travelapp.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.travelapp.ui.App
import com.example.travelapp.user.User

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    val appUser = MutableLiveData<User>()

    init {
        appUser.value = (application as App).getUser()
    }

}