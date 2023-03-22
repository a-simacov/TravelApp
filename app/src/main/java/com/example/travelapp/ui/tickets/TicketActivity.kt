package com.example.travelapp.ui.tickets

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.travelapp.R
import com.example.travelapp.databinding.ActivityPlaceBinding
import com.example.travelapp.databinding.ActivityTicketBinding
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
        val binding = ActivityTicketBinding.inflate(layoutInflater)
        var ticketId = 0
        with (intent) {
            binding.textCityFrom.text = getStringExtra("ticket_city_from")
            binding.textCityTo2.text = getStringExtra("ticket_city_to")
            binding.textDepDate.text = getStringExtra("ticket_arrival_date")
            binding.textArrDate.text = getStringExtra("ticket_departure_date")
            binding.textAirline2.text = getStringExtra("ticket_airline")
            ticketId = getIntExtra("ticket_id", 0)
        }

        // Почему не подключается обработчик нажатия через binding?
        //binding.imgRemoveTicket.setOnClickListener { deleteTicketListener(ticketId) }
        findViewById<ImageView>(R.id.imgRemoveTicket).setOnClickListener { deleteTicketListener(ticketId) }
    }

    private fun deleteTicketListener(ticketId: Int) {
        val dao = Db.getDb(application).getDao()
        val repository = Repository(dao)

        thread { repository.deleteTicketById(ticketId) }
        startActivity(Intent(this, TicketsActivity::class.java))
        finish()
    }

}