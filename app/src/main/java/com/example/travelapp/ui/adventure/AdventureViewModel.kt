package com.example.travelapp.ui.adventure

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.travelapp.db.Db
import com.example.travelapp.db.Places
import com.example.travelapp.db.Repository
import com.example.travelapp.network.RetrofitClient
import com.example.travelapp.user.User
import com.example.travelapp.user.UserRepository
import kotlinx.coroutines.launch

class AdventureViewModel(application: Application) : AndroidViewModel(application) {

    var places = MutableLiveData<MutableList<Places>>()
    private val repository: Repository
    val searchText = MutableLiveData("")
    val appUser = MutableLiveData<User>()

    init {
        val dao = Db.getDb(application).getDao()
        repository = Repository(dao)
        val userRepository = UserRepository(application.applicationContext)

        viewModelScope.launch {
            appUser.value = userRepository.getAppUser()
            try {
                places.value = RetrofitClient.retroifitService.getAdventures()
            } catch (e: java.lang.Exception) {
                Toast.makeText(application.applicationContext, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun delete(place: Places) {
        viewModelScope.launch {
            repository.deletePlace(place)
        }
    }

    fun clear() {
        viewModelScope.launch {
            repository.deletePlaces()
        }
    }

    fun add(place: Places) {
        viewModelScope.launch {
            repository.addPlace(place)
        }
    }

}