package com.example.travelapp.ui.adventure

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.travelapp.db.Db
import com.example.travelapp.db.Places
import com.example.travelapp.db.Repository
import com.example.travelapp.network.RetrofitClient
import com.example.travelapp.tools.getUserNamePrefs
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

class AdventureViewModel(application: Application) : AndroidViewModel(application) {

    var places: LiveData<List<Places>>
    private val repository: Repository
    val searchText = MutableLiveData("")
    var userName = MutableLiveData<String>()

    init {
        val dao = Db.getDb(application).getDao()
        repository = Repository(dao)
        places = repository.places
        userName.value = getUserNamePrefs(application.applicationContext)

//        viewModelScope.launch {
//            try {
//                places.value = RetrofitClient.retroifitService.getAdventures()
//            } catch (e: java.lang.Exception) {
//                Toast.makeText(application.applicationContext, e.message, Toast.LENGTH_LONG).show()
//            }
//        }
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
        val map = mutableMapOf<CoroutineDispatcher, Long>(
            Dispatchers.Main to 0,
            Dispatchers.Unconfined to 0,
            Dispatchers.IO to 0,
            Dispatchers.Default to 0
        )
        map.forEach { (disp, _) ->
            map[disp] = measureTimeMillis {
                CoroutineScope(disp).launch {
                    repository.addPlace(place)
                }
            }
            Log.i("DispatchersTime", "$disp time: ${map[disp]}")
        }

        val winner = map.minBy {it.value}
        Log.i( "DispatchersTime", "Winner is $winner")
    }

}