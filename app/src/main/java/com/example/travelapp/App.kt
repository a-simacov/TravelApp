package com.example.travelapp

import android.app.Application
import com.example.travelapp.receivers.registerBCReceivers
import com.example.travelapp.user.User
import com.example.travelapp.user.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class App : Application() {

    private lateinit var appUser: User
    private lateinit var userRepository: UserRepository

    override fun onCreate() {
        super.onCreate()
        userRepository = UserRepository(applicationContext)
        CoroutineScope(Dispatchers.IO).launch {
            appUser = userRepository.getAppUser()
        }

        registerBCReceivers(applicationContext)
    }

    fun getUser() = appUser

    fun setUser(user: User) {
        appUser = user
    }

    fun getUserRepo(): UserRepository = userRepository

}