package com.example.travelapp.ui

import android.app.Application
import com.example.travelapp.receivers.registerBCReceivers
import com.example.travelapp.user.User
import com.example.travelapp.user.UserRepository
import com.example.travelapp.workers.WMLoader

class App : Application() {

    private lateinit var appUser: User
    private lateinit var userRepository: UserRepository

    override fun onCreate() {
        super.onCreate()
        userRepository = UserRepository(applicationContext)
        appUser = userRepository.getAppUser()

        registerBCReceivers(applicationContext)
    }

    fun getUser() = appUser

    fun setUser(user: User) {
        appUser = user
    }

    fun getUserRepo(): UserRepository = userRepository

}