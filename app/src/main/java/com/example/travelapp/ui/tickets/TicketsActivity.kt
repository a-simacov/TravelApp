package com.example.travelapp.ui.tickets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelapp.R
import com.example.travelapp.adapters.TicketsRecyclerAdapter
import com.example.travelapp.databinding.ActivityTicketsBinding
import com.example.travelapp.databinding.TicketDialogBinding
import com.example.travelapp.db.Ticket
import com.example.travelapp.tools.FlightsCountUpdater
import com.example.travelapp.tools.openSearch
import com.example.travelapp.tools.updateUserImg
import com.google.android.material.bottomsheet.BottomSheetDialog

class TicketsActivity : AppCompatActivity() {
    lateinit var viewModel: TicketsViewModel
    lateinit var dataBinding: ActivityTicketsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_tickets)

        val recyclerView = dataBinding.ticketsRecycler
        recyclerView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL, false
        )

        viewModel = ViewModelProvider(this).get(TicketsViewModel::class.java)
        dataBinding.ticketModel = viewModel
        dataBinding.lifecycleOwner = this
        dataBinding.ticketModel!!.searchText.observe(this) { println(it) }

        // Подписываемся на изменения в tickets из viewModel
        // При изменении tickets, он будет преобразован в MutableList и сообщит адаптеру об изменениях
        viewModel.tickets.observe(this) { it ->
            val adapter = recyclerView.adapter as TicketsRecyclerAdapter
            adapter.tickets = it.toMutableList()
            viewModel.weather.value?.let { weather ->
                adapter.weather = weather
            }
            adapter.notifyDataSetChanged()
        }

        recyclerView.adapter = TicketsRecyclerAdapter(this, viewModel)

        initOnClickListeners()

        FlightsCountUpdater(this).start(dataBinding.tvFlightsCountTickets)

        viewModel.userName.observe(this) {
            dataBinding.tvUserNameTickets.text = it
        }

        updateUserImg(this, dataBinding.ivUserTickets)
    }

    private fun initOnClickListeners() {
        dataBinding.imageViewAddTicket.setOnClickListener{
            showAddTicketDialog()
        }
        dataBinding.btnSearchTickets.setOnClickListener {
            openSearch(this, viewModel.searchText.value)
        }
        dataBinding.ivUserTickets.setOnClickListener {
            //if (AppUser.isAuth) SignOutDialog(this).showAlert()
        }
    }

    private fun showAddTicketDialog() {
        val dialog = BottomSheetDialog(this)
        val dialogBinding = TicketDialogBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        setDialogListeners(dialog, dialogBinding)
        dialog.show()
    }

    private fun setDialogListeners(dialog: BottomSheetDialog, dialogBinding: TicketDialogBinding) {
        with (dialogBinding) {
            btnTicketCancel.setOnClickListener {
                dialog.dismiss()
            }
            btnTicketsDelete.setOnClickListener {
                viewModel.clear()
                dialog.dismiss()
            }
            btnTicketsAdd.setOnClickListener {
                val newTicket = newTicketFromDialog(this)
                viewModel.add(newTicket)
                dialog.dismiss()
            }
        }
    }

    private fun newTicketFromDialog(dialogBinding: TicketDialogBinding): Ticket {
        return with (dialogBinding) {
            Ticket(
                cityFrom = editTextCityFrom.text.toString(),
                departureDate = editTextDepartureDate.text.toString(),
                cityTo = editTextCityTo.text.toString(),
                arrivalDate = editTextArrivalDate.text.toString(),
                airline = editTextAirline.text.toString(),
                qrLink = ""
            )
        }
    }

}