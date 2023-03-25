package com.example.travelapp.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.travelapp.db.Db
import com.example.travelapp.db.Places
import com.example.travelapp.db.Repository

class LoadAdventures(context: Context, workingParameters: WorkerParameters) : Worker(context, workingParameters) {
    override fun doWork(): Result {
        val dao = Db.getDb(applicationContext).getDao()
        val repository = Repository(dao)
        repository.deletePlaces()
        repository.addPlaces(
            listOf(
                Places(null, "place 1", "info 1", "", "detail 1"),
                Places(null, "place 2", "info 2", "", "detail 2")
            )
        )
        return Result.success()
    }
}