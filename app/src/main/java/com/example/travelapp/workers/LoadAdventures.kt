package com.example.travelapp.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.travelapp.db.Db
import com.example.travelapp.db.Places
import com.example.travelapp.db.Repository
import com.example.travelapp.tools.Constants
import com.example.travelapp.tools.sendLocalBroadcastInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoadAdventures(val context: Context, workingParameters: WorkerParameters) : Worker(context, workingParameters) {

    override fun doWork(): Result {
        val dao = Db.getDb(applicationContext).getDao()
        val repository = Repository(dao)

        CoroutineScope(Dispatchers.Unconfined).launch {
            repository.deletePlaces()
            repository.addPlaces(
                listOf(
                    Places(null, "Vietnam", "info 1", "", "detail 1"),
                    Places(null, "Spain", "info 2", "", "detail 2")
                )
            )
        }

        sendLocalBroadcastInfo(context, Constants.LACTION_ADV_UPLOADED, "Uploaded new adventures.")

        return Result.success()
    }

}