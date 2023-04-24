package com.example.travelapp.user

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.travelapp.tools.Constants
import com.example.travelapp.tools.sendLocalBroadcastError
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseAppUserStorage(private val context: Context) {

    private val auth = Firebase.auth

    fun getUser(): User? {
        return auth.currentUser?.let {
            User(name = it.email ?: "")
        }
    }

    fun signUp(email: String, pass: String): MutableLiveData<User> {
        val user = MutableLiveData<User>()
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
            if (task.isSuccessful)
                user.postValue(User(name = email, isAuth = true))
            task.exception?.let {
                sendLocalBroadcastError(context, Constants.LACTION_REPO_ERROR, it.message!!)
            }
        }
        return user
    }

    fun signIn(email: String, pass: String): MutableLiveData<User> {
        val user = MutableLiveData<User>()
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
            if (task.isSuccessful)
                user.postValue(User(name = email, isAuth = true))
            task.exception?.let {
                sendLocalBroadcastError(context, Constants.LACTION_REPO_ERROR, it.message!!)
            }
        }
        return user
    }

    fun signOut() {
        auth.signOut()
    }

}