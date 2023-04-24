package com.example.travelapp.user

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.travelapp.ui.App

class UserRepository(private val context: Context) {

    private val firebaseStorage = FirebaseAppUserStorage(context)
    private val defaultStorage = DefaultAppUserStorage(context)

    fun getAppUser(): User {
        return firebaseStorage.getUser() ?: defaultStorage.getUser()
    }

    fun setAppUser(email: String, pass: String): MutableLiveData<User> {
        val user = firebaseStorage.signIn(email, pass)
        if (user.value?.isAuth == true)
            (context as App).setUser(user.value!!)
        return user
    }

    fun newAppUser(email: String, pass: String): MutableLiveData<User> {
        val user = firebaseStorage.signUp(email, pass)
        if (user.value?.isAuth == true)
            (context as App).setUser(user.value!!)
        return user
    }

    fun resetAppUser() {
        firebaseStorage.signOut()
        val user = defaultStorage.getUser()
        (context as App).setUser(user)
    }

}