package com.example.travelapp.ui.adventure

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.travelapp.R
import com.example.travelapp.databinding.ActivityPlaceBinding
import com.example.travelapp.db.Db
import com.example.travelapp.db.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaceActivity : AppCompatActivity() {

    lateinit var dataBinding: ActivityPlaceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_place)
        setContentView(dataBinding.root)

        fillPlaceData(intent)
    }

    private fun fillPlaceData(intent: Intent) {
        val dao = Db.getDb(application).getDao()
        val repository = Repository(dao)

        val placeId = intent.getIntExtra("place_id", 0)

        CoroutineScope(Dispatchers.Unconfined).launch {
            dataBinding.place = repository.getPlace(placeId)
        }
    }
}