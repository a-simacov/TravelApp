package com.example.travelapp.ui.tickets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.travelapp.R
import com.example.travelapp.adapters.TicketsRecyclerAdapter
import com.example.travelapp.databinding.ActivityTicketsBinding
import com.example.travelapp.databinding.TicketDialogBinding
import com.example.travelapp.db.Db
import com.example.travelapp.db.Repository
import com.example.travelapp.db.Ticket
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.UUID
import kotlin.concurrent.thread

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
            adapter.tickets = it.toMutableList()
            adapter.notifyDataSetChanged()
            Log.i("ticketsObserver", "TICKETS CHANGED")
        }

        recyclerView.adapter = TicketsRecyclerAdapter(viewModel)

        dataBinding.imageViewAddTicket.setOnClickListener{ addTicketOnCLick() }

        attachTicketsUpdate()
    }

    private fun attachTicketsUpdate() {
        val wm = WorkManager.getInstance(applicationContext)
        val tag = "loadTicketsOneTime"
        var workId: UUID? = null
        wm.getWorkInfosByTagLiveData(tag).observe(this) { workInfos ->
            workInfos.forEach {
                if (it.state == WorkInfo.State.ENQUEUED) {
                    workId = it.id
                    Log.i("workState", "WORKINFO ENQUEED ${it.id}: ${it.state}")
                } else if (workId == it.id && it.state == WorkInfo.State.SUCCEEDED) {
                    Log.i("workState", "WORKINFO SUCCEEDED ${it.id}: ${it.state}")
                    val dao = Db.getDb(application).getDao()
                    viewModel.tickets.value = Repository(dao).getAllTickets()
                    return@observe
                }
            }
        }
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

}