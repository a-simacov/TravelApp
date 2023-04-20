package com.example.travelapp.ui.home

import android.app.AlertDialog
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.travelapp.R
import com.example.travelapp.databinding.ActivityHomeBinding
import com.example.travelapp.tools.*
import com.example.travelapp.ui.HotelsActivity
import com.example.travelapp.ui.SignOutDialog
import com.example.travelapp.ui.adventure.AdventureActivity
import com.example.travelapp.ui.tickets.TicketsActivity

class HomeActivity : AppCompatActivity() {

    lateinit var viewModel: HomeViewModel
    lateinit var dataBinding: ActivityHomeBinding

    private val onBackPressedCallback = object : OnBackPressedCallback(AppUser.isAuth) {
        override fun handleOnBackPressed() {
            showDialog()
        }
    }

    private fun showDialog(){
        AlertDialog.Builder(this).apply {
            setTitle(Constants.DIALOG_TITLE_WARN)
            setMessage(Constants.MSG_CLOSE_APP)
            setPositiveButton(Constants.DIALOG_BTN_YES) { _, _ -> finishAffinity() }
            setNegativeButton(Constants.DIALOG_BTN_NO, null)
            show()
        }
    }

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

        updateUserImg(this, dataBinding.ivUserHome)

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private fun initOnClickListeners() {
        dataBinding.tickets.setOnClickListener {
            openActivity(this, TicketsActivity::class.java)
        }
        dataBinding.adventure.setOnClickListener{
            openActivity(this, AdventureActivity::class.java)
        }
        dataBinding.hotels.setOnClickListener{
            openActivity(this, HotelsActivity::class.java)
        }
        dataBinding.ivUserHome.setOnClickListener {
            if (AppUser.isAuth) SignOutDialog(this).showAlert()
        }
    }

}