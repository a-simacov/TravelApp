package com.example.travelapp.ui.tickets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.travelapp.R
import com.example.travelapp.adapters.TicketsRecyclerAdapter
import com.example.travelapp.databinding.ActivityTicketsBinding
import com.example.travelapp.databinding.TicketDialogBinding
import com.example.travelapp.db.Ticket
import com.example.travelapp.tools.Constants
import com.example.travelapp.tools.FlightsCountUpdater
import com.example.travelapp.tools.openSearch
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*

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
        viewModel.tickets.observe(this) {
            val adapter = recyclerView.adapter as TicketsRecyclerAdapter
            println("Tickets observed: ${it?.joinToString()}")
            it?.also {
                adapter.tickets = it.toMutableList()
                adapter.notifyDataSetChanged()
            }
        }

        recyclerView.adapter = TicketsRecyclerAdapter(viewModel)

        dataBinding.imageViewAddTicket.setOnClickListener{ addTicketOnCLick() }
        dataBinding.btnSearchTickets.setOnClickListener {
            openSearch(this, viewModel.searchText.value)
        }

        FlightsCountUpdater(this).start(dataBinding.tvFlightsCountTickets)

        attachTicketsUpdate()
    }

    private fun addTicketOnCLick() {
        val dialog = BottomSheetDialog(this)
        val dialogBinding = TicketDialogBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        setDialogListeners(dialog, dialogBinding)
        dialog.show()
    }

    private fun setDialogListeners(dialog: BottomSheetDialog, dialogBinding: TicketDialogBinding) {
        with (dialogBinding) {
            btnTicketCancel.setOnClickListener { dialog.dismiss() }
            btnTicketsDelete.setOnClickListener {
                viewModel.clear()
                dialog.dismiss()
            }
            btnTicketsAdd.setOnClickListener {
                val newTicket = newTicket(this)
                viewModel.add(newTicket)
                dialog.dismiss()
            }
        }
    }

    private fun newTicket(dialogBinding: TicketDialogBinding): Ticket {
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

    private fun attachTicketsUpdate() {
        var workId: UUID? = null
        WorkManager.getInstance(applicationContext)
            .getWorkInfosByTagLiveData(Constants.TAG_WI_LOAD_TICKETS_SINGLE)
            .observe(this) { workInfos ->
                workInfos.forEach { workInfo ->
                    if (workInfo.state == WorkInfo.State.ENQUEUED) {
                        workId = workInfo.id
                    } else if (workId == workInfo.id && workInfo.state == WorkInfo.State.SUCCEEDED) {
                        viewModel.updateAllTickets()
                        return@observe
                    }
                }
            }
    }

}