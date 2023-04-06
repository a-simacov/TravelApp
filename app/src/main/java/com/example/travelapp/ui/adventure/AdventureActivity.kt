package com.example.travelapp.ui.adventure

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.travelapp.R
import com.example.travelapp.adapters.AdventureRecyclerAdapter
import com.example.travelapp.databinding.ActivityAdventureBinding
import com.example.travelapp.databinding.PlacesDialogBinding
import com.example.travelapp.db.Places
import com.example.travelapp.tools.Constants
import com.example.travelapp.tools.FlightsCountUpdater
import com.example.travelapp.tools.openSearch
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*

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

        recyclerView.adapter = AdventureRecyclerAdapter(viewModel)

        dataBinding.imageViewAddPlace.setOnClickListener{ addPlaceOnCLick() }
        dataBinding.btnSearchAdventure.setOnClickListener {
            openSearch(this, viewModel.searchText.value)
        }

        FlightsCountUpdater(this).start(dataBinding.tvFlightsCountAdv)

        attachPlacesUpdate()
    }

    private fun addPlaceOnCLick() {
        val dialog = BottomSheetDialog(this)
        val dialogBinding = PlacesDialogBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        setDialogListeners(dialog, dialogBinding)
        dialog.show()
    }

    private fun setDialogListeners(dialog: BottomSheetDialog, dialogBinding: PlacesDialogBinding) {
        with (dialogBinding) {
            btnCancel.setOnClickListener { dialog.dismiss() }
            btnDelete.setOnClickListener {
                viewModel.clear()
                dialog.dismiss()
            }
            btnAdd.setOnClickListener {
                val newPlace = newPlace(this)
                viewModel.add(newPlace)
                dialog.dismiss()
            }
        }
    }

    private fun newPlace(dialogBinding: PlacesDialogBinding): Places {
        return with (dialogBinding) {
            Places(
                name = editTextName.text.toString(),
                info = editTextInfo.text.toString(),
                textDetail = editTextDetail.text.toString(),
                imageUrl = ""
            )
        }
    }

    private fun attachPlacesUpdate() {
        var workId: UUID? = null
        WorkManager.getInstance(applicationContext)
            .getWorkInfosByTagLiveData(Constants.TAG_WI_LOAD_ADVS_SINGLE)
            .observe(this) { workInfos ->
                workInfos.forEach { workInfo ->
                    if (workInfo.state == WorkInfo.State.ENQUEUED) {
                        workId = workInfo.id
                    } else if (workId == workInfo.id && workInfo.state == WorkInfo.State.SUCCEEDED) {
                        viewModel.updateAllPlaces()
                        return@observe
                    }
                }
            }
    }

}