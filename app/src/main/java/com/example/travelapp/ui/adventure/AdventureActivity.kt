package com.example.travelapp.ui.adventure

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelapp.R
import com.example.travelapp.adapters.CustomRecyclerAdapter
import com.example.travelapp.db.Places
import com.google.android.material.bottomsheet.BottomSheetDialog

class AdventureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adventure)

        val recyclerView = findViewById<RecyclerView>(R.id.placesRecycler)
        recyclerView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL, false
        )

        val viewModel: AdventureViewModel = ViewModelProvider(this).get(AdventureViewModel::class.java)
        // подписка на переменную places
        viewModel.places.observe(this) {
            val adapter = recyclerView.adapter as CustomRecyclerAdapter
            adapter.places = it.toMutableList()
            adapter.notifyDataSetChanged()
        }

        recyclerView.adapter = CustomRecyclerAdapter(viewModel)

        findViewById<ImageView>(R.id.imageViewAddPlace).setOnClickListener{ addPlaceOnCLick(viewModel) }

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
        val place = Places(
            name = dialog.findViewById<TextView>(R.id.editTextName)?.text.toString(),
            info = dialog.findViewById<TextView>(R.id.editTextInfo)?.text.toString(),
            textDetail = dialog.findViewById<TextView>(R.id.editTextDetail)?.text.toString(),
            imageUrl = ""
        )
        viewModel.add(place)
        dialog.dismiss()
    }

    private fun clearAllPlaces(dialog: BottomSheetDialog, viewModel: AdventureViewModel) {
        viewModel.deletePlaces()
        dialog.dismiss()
    }

}