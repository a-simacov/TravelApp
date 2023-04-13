package com.example.travelapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.travelapp.databinding.ActivitySignUpBinding
import com.example.travelapp.tools.AppUser

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initOnClickListeners()
    }

    private fun initOnClickListeners() {
        binding.btnRegister.setOnClickListener { registerUser() }
    }

    private fun registerUser() {
        val email = binding.etEmailSignUp.text.toString()
        val pass = binding.etPassSignUp.text.toString()
        val passConfirm = binding.etPassConfirmSignUp.text.toString()

        AppUser.signUp(this, email, pass, passConfirm)
    }

}