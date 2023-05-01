package com.example.travelapp.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.travelapp.databinding.ActivitySignUpBinding
import com.example.travelapp.tools.Constants
import com.example.travelapp.tools.openActivity
import com.example.travelapp.tools.showToast
import com.example.travelapp.ui.home.HomeActivity

class SignUpActivity : AppCompatActivity() {

    lateinit var viewModel: SignUpViewModel
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        viewModel.appUser.observe(this) { user ->
            if (user.isAuth) {
                openActivity(this, HomeActivity::class.java)
                finish()
            }
        }

        initOnClickListeners()
    }

    private fun initOnClickListeners() {
        binding.btnRegister.setOnClickListener { registerUser() }
    }

    private fun registerUser() {
        val email = binding.etEmailSignUp.text.toString()
        val pass = binding.etPassSignUp.text.toString()
        val passConfirm = binding.etPassConfirmSignUp.text.toString()

        if (pass == passConfirm)
            viewModel.newUser(email, pass)
        else
            showToast(this, Constants.MSG_DIFFERENT_PASSWORDS)

    }

}