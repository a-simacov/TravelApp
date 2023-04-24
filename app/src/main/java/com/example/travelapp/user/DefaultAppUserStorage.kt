package com.example.travelapp.user

import android.content.Context
import com.example.travelapp.network.NetworkRepo
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DefaultAppUserStorage(private val context: Context) {

    fun getUser(): User {
        var name = "default user name"
        CoroutineScope(Dispatchers.IO).launch {
            name = withContext(this.coroutineContext) { NetworkRepo(context).getDefaultUserName() }
        }
        return User(name = name, isAuth = false)
    }

}