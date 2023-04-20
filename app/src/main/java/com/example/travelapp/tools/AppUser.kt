package com.example.travelapp.tools

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.travelapp.network.NetworkRepo
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
        setUserName(context)
        openHomeIfUserIsAuth(context)
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
        setUserName(context)
        (context as AppCompatActivity).finish()
        openActivity(context, MainActivity::class.java)
    }

    private fun signInUpOnComplete(context: Context, task: Task<AuthResult>) {
        setUserName(context)
        openHomeIfUserIsAuth(context)
        task.exception?.let {
            showToast(context, it.message)
        }
    }

    private fun setDefaultImgUrl(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            imgUrl = NetworkRepo(context).getDefaultUserImgUrl()
        }
    }

    private fun setUserName(context: Context) {
        isAuth = (auth.currentUser != null)
        if (isAuth) {
            name = auth.currentUser?.email!!
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                name = NetworkRepo(context).getDefaultUserName()
            }
        }
    }

    private fun openHomeIfUserIsAuth(context: Context) {
        if (isAuth) {
            openActivity(context, HomeActivity::class.java)
            (context as AppCompatActivity).finish()
        }
    }

}