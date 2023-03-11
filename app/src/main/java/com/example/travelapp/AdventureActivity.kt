package com.example.travelapp

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class AdventureActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adventure)

        Log.i("Lifecycle", "Adventure - onCreate")

        // Пример приемки данных из вызывающего намерения
        if (intent != null) {
            Log.i("ActivitiesComm", intent.getStringExtra("message")!!)
        }
    }

    // Пример отправки данных обратно в намерение
    fun backToHome(view: View) {
        val response = Intent()
        response.putExtra("responseMessage", "I am back from Adventure")
        setResult(RESULT_OK, response)
        finish() // закрыть активность
    }

    // Пример вызова неявного активити без обработки полученного результата
    fun runImplicitIntent(view: View) {
        val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:5551234"))
        try {
            startActivity(callIntent)
        } catch (e: ActivityNotFoundException) {

        }
    }
}