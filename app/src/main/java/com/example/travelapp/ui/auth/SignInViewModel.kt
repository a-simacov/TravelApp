package com.example.travelapp.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.travelapp.user.User
import com.example.travelapp.user.UserRepository
import kotlinx.coroutines.launch

class SignInViewModel(application: Application) : AndroidViewModel(application) {

    private var userRepository: UserRepository
    var appUser = MutableLiveData<User>()

    init {
        userRepository = UserRepository(application.applicationContext)
    }

    fun login(email: String, pass: String) {
        viewModelScope.launch {
            appUser.postValue(userRepository.setAppUser(email, pass))
        }
    }

}