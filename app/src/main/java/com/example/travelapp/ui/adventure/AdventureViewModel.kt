package com.example.travelapp.ui.adventure

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.travelapp.db.Db
import com.example.travelapp.db.Places
import com.example.travelapp.db.Repository
import kotlin.concurrent.thread

class AdventureViewModel(application: Application) : AndroidViewModel(application) {

    var places: LiveData<List<Places>>
    private val repository: Repository

    init {
        val dao = Db.getDb(application).getDao()
        repository = Repository(dao)
        places = repository.places
    }

    fun delete(place: Places) { thread { repository.deletePlace(place) } }

    fun clear() { thread { repository.deletePlaces() } }

    fun add(place: Places) { thread { repository.addPlace(place) } }

}