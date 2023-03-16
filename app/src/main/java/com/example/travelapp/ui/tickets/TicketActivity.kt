package com.example.travelapp.ui.tickets

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.travelapp.R
import com.example.travelapp.db.Db
import com.example.travelapp.db.Repository
import kotlin.concurrent.thread

class TicketActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)
        fillTicketData(intent)
    }

    private fun fillTicketData(intent: Intent) {
        val textCityFrom = findViewById<TextView>(R.id.textCityFrom)
        val textCityTo2 = findViewById<TextView>(R.id.textCityTo2)
        val textDepDate = findViewById<TextView>(R.id.textDepDate)
        val textArrDate = findViewById<TextView>(R.id.textArrDate)
        val textAirline2 = findViewById<TextView>(R.id.textAirline2)
        var ticketId = 0
        with (intent) {
            textCityFrom?.text = getStringExtra("ticket_city_from")
            textCityTo2?.text = getStringExtra("ticket_city_to")
            textDepDate?.text = getStringExtra("ticket_arrival_date")
            textArrDate?.text = getStringExtra("ticket_departure_date")
            textAirline2?.text = getStringExtra("ticket_airline")
            ticketId = getIntExtra("ticket_id", 0)
        }

        val dao = Db.getDb(application).getDao()
        val repository = Repository(dao)

        findViewById<ImageView>(R.id.imgRemoveTicket).setOnClickListener {
            thread { repository.deleteTicketById(ticketId) }
            startActivity(Intent(this, TicketsActivity::class.java))
            finish()
        }

    }

}