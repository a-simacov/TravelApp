package com.example.travelapp.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.travelapp.R
import com.example.travelapp.databinding.ActivityHomeBinding
import com.example.travelapp.tools.FlightsCountUpdater
import com.example.travelapp.ui.adventure.AdventureActivity
import com.example.travelapp.ui.tickets.TicketsActivity
import com.example.travelapp.ui.tickets.TicketsViewModel

class HomeActivity : AppCompatActivity() {

    lateinit var viewModel: HomeViewModel
    lateinit var dataBinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        setContentView(dataBinding.root)

        initOnClickListeners()
        FlightsCountUpdater(this).start(dataBinding.tvFlightsCountHome)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        dataBinding.homeModel = viewModel
        dataBinding.lifecycleOwner = this
        viewModel.userName.observe(this) {
            dataBinding.tvUserNameHome.text = it
        }
    }

    private fun initOnClickListeners() {
        dataBinding.tickets.setOnClickListener {
            openActivity(TicketsActivity::class.java)
        }
        dataBinding.adventure.setOnClickListener{
            openActivity(AdventureActivity::class.java)
        }
    }

    private fun openActivity(cls: Class<*>) {
        this.startActivity(
            Intent(this, cls)
        )
    }

}