package com.example.travelapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts

class HomeActivity : AppCompatActivity() {
    // Пример обработки результата из вызванного намерения
    private var advLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
        result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val response = result.data?.getStringExtra("responseMessage") ?: ""
            Log.i("ActivitiesComm", response)
            // обрабатываем наш ответ
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    fun adventureOnClick(view: View) {
        val sender = Intent(this, AdventureActivity::class.java)
        sender.putExtra("message", "i am from Home activity")
        advLauncher.launch(sender)
    }
}