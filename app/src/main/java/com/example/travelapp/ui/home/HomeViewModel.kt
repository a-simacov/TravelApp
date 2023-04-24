package com.example.travelapp.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.travelapp.user.User
import com.example.travelapp.user.UserRepository

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    val appUser = MutableLiveData<User>()

    init {
        val userRepository = UserRepository(application.applicationContext)
        appUser.value = userRepository.getAppUser()
    }

}