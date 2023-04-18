package com.example.travelapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.travelapp.databinding.ActivityMainBinding
import com.example.travelapp.receivers.*
import com.example.travelapp.tools.AppUser
import com.example.travelapp.tools.openActivity
import com.example.travelapp.ui.home.HomeActivity
import com.example.travelapp.workers.WMLoader

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppUser.initUser(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initOnClickListeners()
        WMLoader(this).loadData()
        registerBCReceivers(applicationContext)
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