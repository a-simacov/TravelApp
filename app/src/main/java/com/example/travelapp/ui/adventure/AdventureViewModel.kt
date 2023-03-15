package com.example.travelapp.ui.adventure

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.travelapp.db.AdventureRepository
import com.example.travelapp.db.Db
import com.example.travelapp.db.Places
import kotlin.concurrent.thread

class AdventureViewModel(application: Application) : AndroidViewModel(application) {

    var places: LiveData<List<Places>>
    private val repository: AdventureRepository

    init {
        val dao = Db.getDb(application).getDao()
        repository = AdventureRepository(dao)
        places = repository.places
    }

    fun delete(place: Places) { thread { repository.delete(place) } }

    fun deletePlaces() { thread { repository.deletePlaces() } }

    fun add(place: Places) { thread { repository.addPlace(place) } }

}