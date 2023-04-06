package com.example.travelapp.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.travelapp.R
import com.example.travelapp.databinding.TicketItemBinding
import com.example.travelapp.db.Ticket
import com.example.travelapp.ui.tickets.TicketActivity
import com.example.travelapp.ui.tickets.TicketsViewModel

class TicketsRecyclerAdapter(private val viewModel: TicketsViewModel) : RecyclerView.Adapter<TicketsRecyclerAdapter.MyViewHolder>() {
    var tickets = mutableListOf<Ticket>()

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
        with (holder.binding) {
            this.ticket = ticket
            cLayoutTicket.setOnClickListener { openTicket(this, ticket) }
            btnDeleteTicket.setOnClickListener { deleteTicket(ticket, position) }
        }
    }

    override fun getItemCount(): Int {
        return tickets.size
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
        notifyItemRemoved(position)
    }
}