package com.example.travelapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.travelapp.databinding.ActivityHomeBinding
import com.example.travelapp.ui.adventure.AdventureActivity
import com.example.travelapp.ui.tickets.TicketsActivity

class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initOnClickListeners()
    }

    private fun initOnClickListeners() {
        binding.tickets.setOnClickListener {
            openActivity(TicketsActivity::class.java)
        }
        binding.adventure.setOnClickListener{
            openActivity(AdventureActivity::class.java)
        }
    }

    private fun openActivity(cls: Class<*>) {
        this.startActivity(
            Intent(this, cls)
        )
    }

}