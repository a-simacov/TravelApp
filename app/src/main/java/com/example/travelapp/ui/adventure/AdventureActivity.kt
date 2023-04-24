package com.example.travelapp.ui.adventure

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelapp.R
import com.example.travelapp.adapters.AdventureRecyclerAdapter
import com.example.travelapp.databinding.ActivityAdventureBinding
import com.example.travelapp.databinding.PlacesDialogBinding
import com.example.travelapp.db.Places
import com.example.travelapp.tools.FlightsCountUpdater
import com.example.travelapp.tools.openSearch
import com.example.travelapp.tools.updateUserImg
import com.google.android.material.bottomsheet.BottomSheetDialog

class AdventureActivity : AppCompatActivity() {
    lateinit var viewModel: AdventureViewModel
    lateinit var dataBinding: ActivityAdventureBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_adventure)

        val recyclerView = dataBinding.placesRecycler
        recyclerView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL, false
        )

        viewModel = ViewModelProvider(this).get(AdventureViewModel::class.java)
        dataBinding.adventureModel = viewModel
        dataBinding.lifecycleOwner = this
        dataBinding.adventureModel!!.searchText.observe(this) { println(it) }

        // подписка на переменную places
        viewModel.places.observe(this) {
            val adapter = recyclerView.adapter as AdventureRecyclerAdapter
            adapter.places = it.toMutableList()
            adapter.notifyDataSetChanged()
        }

        recyclerView.adapter = AdventureRecyclerAdapter(this, viewModel)

        initOnClickListeners()

        FlightsCountUpdater(this).start(dataBinding.tvFlightsCountAdv)

        viewModel.userName.observe(this) {
            dataBinding.tvUserNameAdv.text = it
        }

        updateUserImg(this, dataBinding.ivUserAdventure)
    }

    private fun initOnClickListeners() {
        dataBinding.imageViewAddPlace.setOnClickListener{
            showAddPlaceDialog()
        }
        dataBinding.btnSearchAdventure.setOnClickListener {
            openSearch(this, viewModel.searchText.value)
        }
        dataBinding.ivUserAdventure.setOnClickListener {
            //if (AppUser.isAuth) SignOutDialog(this).showAlert()
        }
    }

    private fun showAddPlaceDialog() {
        val dialog = BottomSheetDialog(this)
        val dialogBinding = PlacesDialogBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        setDialogListeners(dialog, dialogBinding)
        dialog.show()
    }

    private fun setDialogListeners(dialog: BottomSheetDialog, dialogBinding: PlacesDialogBinding) {
        with (dialogBinding) {
            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
            btnDelete.setOnClickListener {
                viewModel.clear()
                dialog.dismiss()
            }
            btnAdd.setOnClickListener {
                val newPlace = newPlaceFromDialog(this)
                viewModel.add(newPlace)
                dialog.dismiss()
            }
        }
    }

    private fun newPlaceFromDialog(dialogBinding: PlacesDialogBinding): Places {
        return with (dialogBinding) {
            Places(
                name = editTextName.text.toString(),
                info = editTextInfo.text.toString(),
                textDetail = editTextDetail.text.toString(),
                imageUrl = ""
            )
        }
    }
}