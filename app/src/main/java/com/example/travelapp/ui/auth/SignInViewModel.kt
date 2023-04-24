package com.example.travelapp.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.travelapp.user.User
import com.example.travelapp.user.UserRepository

class SignInViewModel(application: Application) : AndroidViewModel(application) {

    private var userRepository: UserRepository
    var appUser = MutableLiveData<User>()

    init {
        userRepository = UserRepository(application.applicationContext)
    }

    fun login(email: String, pass: String) {
        appUser = userRepository.setAppUser(email, pass)
    }

}