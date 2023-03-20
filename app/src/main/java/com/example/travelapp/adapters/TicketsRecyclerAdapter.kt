package com.example.travelapp.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.travelapp.R
import com.example.travelapp.db.Db
import com.example.travelapp.db.Ticket
import com.example.travelapp.ui.tickets.TicketActivity
import com.example.travelapp.ui.tickets.TicketsViewModel

class TicketsRecyclerAdapter(private val viewModel: TicketsViewModel) : RecyclerView.Adapter<TicketsRecyclerAdapter.MyViewHolder>() {
    var tickets = mutableListOf<Ticket>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textAirline: TextView = itemView.findViewById(R.id.textAirline)
        val textCityTo: TextView = itemView.findViewById(R.id.textCityTo)
        val textArrivalDate: TextView = itemView.findViewById(R.id.textArrivalDate)
        val image: ImageView = itemView.findViewById(R.id.imgTicket)
        val cLayoutTicket: ConstraintLayout = itemView.findViewById(R.id.cLayoutTicket)
        val btnDeleteTicket: ImageButton = itemView.findViewById(R.id.btnDeleteTicket)

        var db = Db.getDb(itemView.context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.ticket_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val ticket: Ticket = tickets[position]
        holder.textCityTo.text = ticket.cityTo
        holder.textAirline.text = ticket.airline
        holder.textArrivalDate.text = ticket.arrivalDate
        holder.cLayoutTicket.setOnClickListener { openTicket(holder, ticket) }
        holder.btnDeleteTicket.setOnClickListener { deleteTicket(ticket, position) }
    }

    override fun getItemCount(): Int {
        return tickets.size
    }

    private fun openTicket(holder: MyViewHolder, ticket: Ticket) {
        val context = holder.cLayoutTicket.context as Activity
        val intent = Intent(context, TicketActivity::class.java)
        with (intent) {
            putExtra("ticket_city_from", ticket.cityFrom)
            putExtra("ticket_city_to", ticket.cityTo)
            putExtra("ticket_arrival_date", ticket.arrivalDate)
            putExtra("ticket_departure_date", ticket.departureDate)
            putExtra("ticket_airline", ticket.airline)
            putExtra("ticket_id", ticket.id)
        }
        context.startActivity(intent)
        context.finish()
    }

    private fun deleteTicket(ticket: Ticket, position: Int) {
        viewModel.delete(ticket)
        tickets.removeAt(position)
        notifyItemRemoved(position)
    }
}