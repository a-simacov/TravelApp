package com.example.travelapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.travelapp.databinding.ActivityMainBinding
import com.example.travelapp.tools.openActivity
import com.example.travelapp.ui.auth.SignInActivity
import com.example.travelapp.ui.auth.SignUpActivity
import com.example.travelapp.ui.home.HomeActivity
import com.example.travelapp.workers.WMLoader

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.appUser.observe(this) { user ->
            if (user.isAuth) {
                openActivity(this, HomeActivity::class.java)
                finish()
            }
        }

        WMLoader(this).loadData()

        initOnClickListeners()
    }

    private fun initOnClickListeners() {
        binding.btnSignIn.setOnClickListener {
            openActivity(this, SignInActivity::class.java)
        }
        binding.btnSignUp.setOnClickListener {
            openActivity(this, SignUpActivity::class.java)
        }
        binding.skipText.setOnClickListener {
            openActivity(this, HomeActivity::class.java)
        }
    }

}