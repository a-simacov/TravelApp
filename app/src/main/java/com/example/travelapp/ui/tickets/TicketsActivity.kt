package com.example.travelapp.ui.tickets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelapp.R
import com.example.travelapp.adapters.TicketsRecyclerAdapter
import com.example.travelapp.databinding.ActivityTicketsBinding
import com.example.travelapp.databinding.TicketItemBinding
import com.example.travelapp.db.Ticket
import com.google.android.material.bottomsheet.BottomSheetDialog

class TicketsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tickets)

        val binding = DataBindingUtil.setContentView<ActivityTicketsBinding>(this, R.layout.activity_tickets)

        val recyclerView = findViewById<RecyclerView>(R.id.ticketsRecycler)
        recyclerView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL, false
        )

        val viewModel: TicketsViewModel = ViewModelProvider(this).get(TicketsViewModel::class.java)
        binding.ticketModel = viewModel
        binding.lifecycleOwner = this
        binding.ticketModel!!.searchText.observe(this) { println(it) }

        // Подписываемся на изменения в tickets из viewModel
        // При изменении tickets, он будет преобразован в MutableList и сообщит адаптеру об изменениях
        viewModel.tickets.observe(this) {
            val adapter = recyclerView.adapter as TicketsRecyclerAdapter
            adapter.tickets = it.toMutableList()
            adapter.notifyDataSetChanged()
        }

        recyclerView.adapter = TicketsRecyclerAdapter(viewModel)

        binding.imageViewAddTicket.setOnClickListener{ addTicketOnCLick(viewModel) }

    }

    private fun addTicketOnCLick(viewModel: TicketsViewModel) {
        val dialog = BottomSheetDialog(this)
        with (dialog) {
            setContentView(R.layout.ticket_dialog)
            findViewById<Button>(R.id.btnTicketCancel)?.setOnClickListener { this.dismiss() }
            findViewById<Button>(R.id.btnTicketsDelete)?.setOnClickListener { clearAllTickets(this, viewModel) }
            findViewById<Button>(R.id.btnTicketsAdd)?.setOnClickListener { saveTicket(this, viewModel) }
            show()
        }
    }

    private fun saveTicket(dialog: BottomSheetDialog, viewModel: TicketsViewModel) {
        with (dialog) {
            val ticket = Ticket(
                cityFrom = findViewById<TextView>(R.id.editTextCityFrom)?.text.toString(),
                departureDate = findViewById<TextView>(R.id.editTextDepartureDate)?.text.toString(),
                cityTo = findViewById<TextView>(R.id.editTextCityTo)?.text.toString(),
                arrivalDate = findViewById<TextView>(R.id.editTextArrivalDate)?.text.toString(),
                airline = findViewById<TextView>(R.id.editTextAirline)?.text.toString(),
                qrLink = ""
            )
            viewModel.add(ticket)
            dismiss()
        }
    }

    private fun clearAllTickets(dialog: BottomSheetDialog, viewModel: TicketsViewModel) {
        viewModel.clear()
        dialog.dismiss()
    }

}