package com.example.travelapp.ui.tickets

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import com.example.travelapp.R
import com.example.travelapp.databinding.ActivityTicketBinding
import com.example.travelapp.db.Db
import com.example.travelapp.db.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TicketActivity : AppCompatActivity() {

    lateinit var databinding: ActivityTicketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        databinding = DataBindingUtil.setContentView(this, R.layout.activity_ticket)
        setContentView(databinding.root)

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
        val dao = Db.getDb(application).getDao()
        val repository = Repository(dao)

        val ticketId = intent.getIntExtra("ticket_id", 0)

        CoroutineScope(Dispatchers.Unconfined).launch {
            databinding.ticket = repository.getTicket(ticketId)
        }

        databinding.imgRemoveTicket.setOnClickListener { deleteTicketListener(ticketId) }
    }

    private fun deleteTicketListener(ticketId: Int) {
        val dao = Db.getDb(application).getDao()
        val repository = Repository(dao)
        CoroutineScope(Dispatchers.Unconfined).launch {
            repository.deleteTicketById(ticketId)
        }
        openTicketsActivity()
    }

    private fun openTicketsActivity() {
        startActivity(Intent(this, TicketsActivity::class.java))
        finish()
    }

}