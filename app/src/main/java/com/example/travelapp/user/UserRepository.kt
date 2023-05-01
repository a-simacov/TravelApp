package com.example.travelapp.user

import android.content.Context
import com.example.travelapp.network.NetworkRepo
import com.example.travelapp.App

class UserRepository(private val context: Context) {

    private val firebaseStorage = FirebaseAppUserStorage(context)
    private val defaultStorage = DefaultAppUserStorage(context)

    suspend fun getAppUser(): User {
        val user = firebaseStorage.getUser() ?: defaultStorage.getUser()
        user.imgUrl = NetworkRepo(context).getDefaultUserImgUrl()
        return user
    }

    suspend fun setAppUser(email: String, pass: String): User {
        firebaseStorage.signIn(email, pass)
        return getAppUser().also { user ->
            (context as App).setUser(user)
        }
    }

    suspend fun newAppUser(email: String, pass: String): User {
        firebaseStorage.signUp(email, pass)
        return getAppUser().also { user ->
            (context as App).setUser(user)
        }
    }

    suspend fun resetAppUser() {
        firebaseStorage.signOut()
        (context as App).setUser(
            defaultStorage.getUser()
        )
    }

}