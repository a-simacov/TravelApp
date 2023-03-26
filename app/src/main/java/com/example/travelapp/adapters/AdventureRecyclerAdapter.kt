package com.example.travelapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.travelapp.R
import com.example.travelapp.databinding.PlaceItemBinding
import com.example.travelapp.db.Places
import com.example.travelapp.ui.adventure.AdventureViewModel
import com.example.travelapp.ui.adventure.PlaceActivity

class AdventureRecyclerAdapter(private val viewModel: AdventureViewModel) : RecyclerView.Adapter<AdventureRecyclerAdapter.MyViewHolder>() {
    var places = mutableListOf<Places>()

    // класс PlaceItemBinding - сгенерированный
    class MyViewHolder(val binding: PlaceItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: PlaceItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.place_item,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val place: Places = places[position]
        with (holder.binding) {
            this.place = place
            cLayoutPlace.setOnClickListener { openPlace(this, place) }
            btnDeletePlace.setOnClickListener { deletePlace(place, position) }
        }
    }

    override fun getItemCount(): Int {
        return places.size
    }

    private fun openPlace(binding: PlaceItemBinding, place: Places) {
        val context = binding.cLayoutPlace.context
        val intent = Intent(context, PlaceActivity::class.java)
        intent.putExtra("place_id", place.id)
        context.startActivity(intent)
    }

    private fun deletePlace(place: Places, position: Int) {
        viewModel.delete(place)
        places.removeAt(position)
        notifyItemRemoved(position)
    }
}