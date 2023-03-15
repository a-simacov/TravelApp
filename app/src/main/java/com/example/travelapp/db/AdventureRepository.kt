package com.example.travelapp.db

import androidx.lifecycle.LiveData

class AdventureRepository(private val dao: Dao) {
    val places: LiveData<List<Places>> = dao.getAllPlaces()

    fun delete(place: Places) { dao.deletePlace(place) }

    fun deletePlaces() { dao.deletePlaces() }

    fun addPlace(place: Places) { dao.insertPlace(place) }
}