package com.example.travelapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.travelapp.R
import com.example.travelapp.ui.adventure.AdventureActivity
import com.example.travelapp.ui.tickets.TicketsActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        findViewById<ConstraintLayout>(R.id.tickets).setOnClickListener{
            this.startActivity(
                Intent(this, TicketsActivity::class.java)
            )
        }
        findViewById<ConstraintLayout>(R.id.adventure).setOnClickListener{
            this.startActivity(
                Intent(this, AdventureActivity::class.java)
            )
        }
    }
}