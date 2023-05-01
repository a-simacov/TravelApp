package com.example.travelapp.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.travelapp.user.User
import com.example.travelapp.user.UserRepository
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    val appUser = MutableLiveData<User>()

    init {
        val userRepository = UserRepository(application.applicationContext)
        viewModelScope.launch {
            appUser.value = userRepository.getAppUser()
        }
    }

}