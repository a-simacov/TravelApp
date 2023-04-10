package com.example.travelapp.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.example.travelapp.R
import com.example.travelapp.databinding.TicketItemBinding
import com.example.travelapp.db.Ticket
import com.example.travelapp.network.CityWeather
import com.example.travelapp.ui.tickets.TicketActivity
import com.example.travelapp.ui.tickets.TicketsViewModel

class TicketsRecyclerAdapter(private val context: Context, private val viewModel: TicketsViewModel) : RecyclerView.Adapter<TicketsRecyclerAdapter.MyViewHolder>() {
    var tickets = mutableListOf<Ticket>()
    var weather = mutableMapOf<String, CityWeather>()

    class MyViewHolder(val binding: TicketItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: TicketItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.ticket_item,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val ticket: Ticket = tickets[position]
        with (holder) {
            binding.ticket = ticket
            binding.cLayoutTicket.setOnClickListener { openTicket(binding, ticket) }
            binding.btnDeleteTicket.setOnClickListener { deleteTicket(ticket, position) }
            updateWeather(binding, ticket.cityTo)
        }
    }

    override fun getItemCount(): Int {
        return tickets.size
    }

    private fun updateWeather(binding: TicketItemBinding, cityName: String) {
        val cityWeather = weather.getOrDefault(cityName, null) ?: return
        binding.cityTemperature = cityWeather.current.temp_c.toString()
        try {
            val imgUrl = "http:${cityWeather.current.condition.icon}"
            Glide.with(context)
                .load(imgUrl)
                .into(binding.imgTicket)
        } catch (e: GlideException) {
            println("Error Glide: " + e.logRootCauses("GGGG"))
        }
    }

    private fun openTicket(binding: TicketItemBinding, ticket: Ticket) {
        val context = binding.cLayoutTicket.context as Activity
        val intent = Intent(context, TicketActivity::class.java)
        intent.putExtra("ticket_id", ticket.id)
        context.startActivity(intent)
        context.finish()
    }

    private fun deleteTicket(ticket: Ticket, position: Int) {
        viewModel.delete(ticket)
        tickets.removeAt(position)
        notifyItemRemoved(position)
    }
}