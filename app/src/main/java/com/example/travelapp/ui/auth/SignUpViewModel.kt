package com.example.travelapp.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.travelapp.tools.Constants
import com.example.travelapp.tools.showToast
import com.example.travelapp.user.User
import com.example.travelapp.user.UserRepository

class SignUpViewModel(application: Application) : AndroidViewModel(application) {

    private var userRepository: UserRepository
    var appUser = MutableLiveData<User>()

    init {
        userRepository = UserRepository(application.applicationContext)
    }

    fun newUser(email: String, pass: String, passConfirm: String) {
        if (pass == passConfirm)
            appUser = userRepository.newAppUser(email, pass)
//        else
//            showToast(context, Constants.MSG_DIFFERENT_PASSWORDS)
    }

}