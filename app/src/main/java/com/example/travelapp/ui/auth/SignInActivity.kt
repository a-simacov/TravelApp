package com.example.travelapp.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.travelapp.databinding.ActivitySignInBinding
import com.example.travelapp.tools.openActivity
import com.example.travelapp.ui.home.HomeActivity

class SignInActivity : AppCompatActivity() {

    lateinit var viewModel: SignInViewModel
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)

        viewModel.appUser.observe(this) { user ->
            if (user.isAuth) {
                openActivity(this, HomeActivity::class.java)
                finish()
            }
        }

        initOnClickListeners()
    }

    private fun initOnClickListeners() {
        binding.btnLogin.setOnClickListener { loginUser() }
    }

    private fun loginUser() {
        val email = binding.etEmailSignIn.text.toString()
        val pass = binding.etPassSignIn.text.toString()
        viewModel.login(email, pass)
    }

}