package com.example.travelapp.user

import android.content.Context
import com.example.travelapp.network.NetworkRepo

class DefaultAppUserStorage(private val context: Context) {

    suspend fun getUser(): User {
        val userMap = NetworkRepo(context).getDefaultUser()

        return User(
            name = userMap.getValue("name"),
            imgUrl = userMap.getValue("image"),
            isAuth = false
        )
    }

}