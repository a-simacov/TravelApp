package com.example.travelapp.adapters

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
import com.example.travelapp.db.Places
import com.example.travelapp.ui.PlaceActivity

class CustomRecyclerAdapter(private val places: List<Places>) : RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val infoTextView: TextView = itemView.findViewById(R.id.textInfo)
        val nameTextView: TextView = itemView.findViewById(R.id.textPlace)
        val image: ImageView = itemView.findViewById(R.id.imgPlace)
        val constraintLayout: ConstraintLayout = itemView.findViewById(R.id.constraintLayout)
        val btnDeletePlace: ImageButton = itemView.findViewById(R.id.btnDeletePlace)

        var db = Db.getDb(itemView.context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val place: Places = places[position]
        holder.nameTextView.text = place.name
        holder.infoTextView.text = place.info
        holder.constraintLayout.setOnClickListener { openPlace(holder, place) }
        holder.btnDeletePlace.setOnClickListener { deletePlace(holder, place) }
    }

    override fun getItemCount(): Int {
        return places.size
    }

    private fun openPlace(holder: MyViewHolder, place: Places) {
        val context = holder.constraintLayout.context
        val intent = Intent(context, PlaceActivity::class.java)
        with (intent) {
            putExtra("place_name", place.name)
            putExtra("place_info", place.info)
            putExtra("place_textDetail", place.textDetail)
        }
        context.startActivity(intent)
    }

    private fun deletePlace(holder: MyViewHolder, place: Places) {
        Thread {
            holder.db.getDao().deletePlace(place)
        }.start()
    }
}