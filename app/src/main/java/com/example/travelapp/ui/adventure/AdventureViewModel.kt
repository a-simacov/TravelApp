package com.example.travelapp.ui.adventure

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.travelapp.db.Db
import com.example.travelapp.db.Places
import com.example.travelapp.db.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

class AdventureViewModel(application: Application) : AndroidViewModel(application) {

    var places = MutableLiveData<List<Places>>(emptyList())
    val searchText = MutableLiveData("")
    private val repository: Repository

    init {
        val dao = Db.getDb(application).getDao()
        repository = Repository(dao)
        updateAllPlaces()
    }

    fun updateAllPlaces() {
        CoroutineScope(Dispatchers.IO).launch {
            places.postValue(repository.getAllPlaces())
        }
    }


    fun delete(place: Places) {
        CoroutineScope(Dispatchers.Unconfined).launch {
            repository.deletePlace(place)
        }
    }

    fun clear() {
        CoroutineScope(Dispatchers.Unconfined).launch {
            repository.deletePlaces()
        }
    }

    fun add(place: Places) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.addPlace(place)
        }
    }

}