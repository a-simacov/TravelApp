package com.example.travelapp.ui.adventure

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelapp.R
import com.example.travelapp.adapters.AdventureRecyclerAdapter
import com.example.travelapp.databinding.ActivityAdventureBinding
import com.example.travelapp.db.Places
import com.google.android.material.bottomsheet.BottomSheetDialog

class AdventureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityAdventureBinding>(this, R.layout.activity_adventure)

        val recyclerView = findViewById<RecyclerView>(R.id.placesRecycler)
        recyclerView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL, false
        )

        val viewModel: AdventureViewModel = ViewModelProvider(this).get(AdventureViewModel::class.java)
        binding.adventureModel = viewModel
        binding.lifecycleOwner = this
        binding.adventureModel!!.searchText.observe(this) { println(it) /*binding.adventureModel.searchText.value = "sss"*/ }

        // подписка на переменную places
        viewModel.places.observe(this) {
            val adapter = recyclerView.adapter as AdventureRecyclerAdapter
            adapter.places = it.toMutableList()
            adapter.notifyDataSetChanged()
        }

        recyclerView.adapter = AdventureRecyclerAdapter(viewModel)

        binding.imageViewAddPlace.setOnClickListener{ addPlaceOnCLick(viewModel) }

    }

    private fun addPlaceOnCLick(viewModel: AdventureViewModel) {
        val dialog = BottomSheetDialog(this)
        with (dialog) {
            setContentView(R.layout.places_dialog)
            findViewById<Button>(R.id.btnCancel)?.setOnClickListener { this.dismiss() }
            findViewById<Button>(R.id.btnDelete)?.setOnClickListener { clearAllPlaces(this, viewModel) }
            findViewById<Button>(R.id.btnAdd)?.setOnClickListener { savePlace(this, viewModel) }
            show()
        }
    }

    private fun savePlace(dialog: BottomSheetDialog, viewModel: AdventureViewModel) {
        with (dialog) {
            val place = Places(
                name = findViewById<TextView>(R.id.editTextName)?.text.toString(),
                info = findViewById<TextView>(R.id.editTextInfo)?.text.toString(),
                textDetail = findViewById<TextView>(R.id.editTextDetail)?.text.toString(),
                imageUrl = ""
            )
            viewModel.add(place)
            dismiss()
        }
    }

    private fun clearAllPlaces(dialog: BottomSheetDialog, viewModel: AdventureViewModel) {
        viewModel.clear()
        dialog.dismiss()
    }

}