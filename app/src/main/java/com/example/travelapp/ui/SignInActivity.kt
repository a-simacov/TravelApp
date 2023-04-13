package com.example.travelapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.travelapp.databinding.ActivitySignInBinding
import com.example.travelapp.tools.AppUser

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initOnClickListeners()
    }

    private fun initOnClickListeners() {
        binding.btnLogin.setOnClickListener { loginUser() }
    }

    private fun loginUser() {
        val email = binding.etEmailSignIn.text.toString()
        val pass = binding.etPassSignIn.text.toString()
        AppUser.signIn(this, email, pass)
    }

}