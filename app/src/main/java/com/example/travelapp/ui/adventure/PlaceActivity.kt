package com.example.travelapp.ui.adventure

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

        dataBinding.textName.setOnClickListener {
            openMap(dataBinding.textName.text.toString())
        }
    }

    private fun openMap(name: String) {
        val uri: Uri = Uri.parse("yandexmaps://maps.yandex.ru/")
            .buildUpon()
            .appendQueryParameter("text", name)
            .appendQueryParameter("z", "12")
            .appendQueryParameter("l", "sat")
            .build()
        val intent = Intent(Intent.ACTION_VIEW, uri)

        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            openMarket()
        }
    }

    private fun openMarket() {
        intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("market://details?id=ru.yandex.yandexmaps")
        startActivity(intent)
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