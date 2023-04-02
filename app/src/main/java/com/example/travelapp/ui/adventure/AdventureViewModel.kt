package com.example.travelapp.ui.adventure

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.travelapp.db.Db
import com.example.travelapp.db.Places
import com.example.travelapp.db.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdventureViewModel(application: Application) : AndroidViewModel(application) {

    var places: LiveData<List<Places>>
    private val repository: Repository
    val searchText = MutableLiveData("")

    init {
        val dao = Db.getDb(application).getDao()
        repository = Repository(dao)
        places = repository.places
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
        CoroutineScope(Dispatchers.Unconfined).launch {
            repository.addPlace(place)
        }
    }

}