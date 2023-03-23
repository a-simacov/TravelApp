package com.example.travelapp.ui.tickets

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.example.travelapp.databinding.ActivityTicketBinding
import com.example.travelapp.db.Db
import com.example.travelapp.db.Repository
import kotlin.concurrent.thread

class TicketActivity : AppCompatActivity() {

    lateinit var binding: ActivityTicketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTicketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fillTicketData(intent)

        onBackPressedDispatcher.addCallback(
            this,
            object: OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    openTicketsActivity()
                }
            }
        )
    }

    private fun fillTicketData(intent: Intent) {
        var ticketId = 0
        with (intent) {
            binding.textCityFrom.text = getStringExtra("ticket_city_from")
            binding.textCityTo2.text = getStringExtra("ticket_city_to")
            binding.textDepDate.text = getStringExtra("ticket_arrival_date")
            binding.textArrDate.text = getStringExtra("ticket_departure_date")
            binding.textAirline2.text = getStringExtra("ticket_airline")
            ticketId = getIntExtra("ticket_id", 0)
        }

        binding.imgRemoveTicket.setOnClickListener { deleteTicketListener(ticketId) }
    }

    private fun deleteTicketListener(ticketId: Int) {
        val dao = Db.getDb(application).getDao()
        val repository = Repository(dao)

        thread { repository.deleteTicketById(ticketId) }
        openTicketsActivity()
    }

    private fun openTicketsActivity() {
        startActivity(Intent(this, TicketsActivity::class.java))
        finish()
    }

}