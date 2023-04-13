package com.example.travelapp.tools

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.travelapp.network.RetrofitClient
import com.example.travelapp.ui.MainActivity
import com.example.travelapp.ui.home.HomeActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object AppUser {

    var name = ""
    var imgUrl = ""
    var isAuth = false

    private val auth = Firebase.auth

    fun initUser(context: Context) {
        setDefaultImgUrl(context)
        if (auth.currentUser != null) {
            setAuthUserName(context)
        } else {
            setDefaultUserName(context)
        }
    }

    fun signUp(context: Context, email: String, pass: String, passConfirm: String) {
        if (pass == passConfirm)
            auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                signInUpOnComplete(context, it)
            }
        else
            showToast(context, Constants.MSG_DIFFERENT_PASSWORDS)
    }

    fun signIn(context: Context, email: String, pass: String) {
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
            signInUpOnComplete(context, it)
        }
    }

    fun signOut(context: Context) {
        auth.signOut()
        (context as AppCompatActivity).finish()
        openActivity(context, MainActivity::class.java)
        setDefaultUserName(context)
    }

    private fun signInUpOnComplete(context: Context, task: Task<AuthResult>) {
        if (task.isSuccessful) {
            setAuthUserName(context)
        }
        task.exception?.let {
            showToast(context, it.message)
        }
    }

    private fun setAuthUserName(context: Context) {
        isAuth = true
        name = auth.currentUser?.email!!
        openActivity(context, HomeActivity::class.java)
        (context as AppCompatActivity).finish()
    }

    private fun setDefaultImgUrl(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val defaultUser = RetrofitClient.retroifitService.getUser()
                imgUrl = defaultUser.getValue("image")
            } catch (e: Exception) {
                (context as AppCompatActivity).runOnUiThread {
                    showToast(context, e.message)
                }
            }
        }
    }

    private fun setDefaultUserName(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val defaultUser = RetrofitClient.retroifitService.getUser()
                name = defaultUser.getValue("name")
                isAuth = false
            } catch (e: Exception) {
                (context as AppCompatActivity).runOnUiThread {
                    showToast(context, e.message)
                }
            }
        }
    }

}