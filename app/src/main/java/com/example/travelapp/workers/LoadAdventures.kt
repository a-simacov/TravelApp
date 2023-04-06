package com.example.travelapp.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.travelapp.db.Db
import com.example.travelapp.db.Places
import com.example.travelapp.db.Repository
import com.example.travelapp.db.Ticket
import com.example.travelapp.tools.Constants
import com.example.travelapp.tools.sendLocalBroadcastInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

class LoadAdventures(val context: Context, workingParameters: WorkerParameters) : Worker(context, workingParameters) {

    override fun doWork(): Result {
        val dao = Db.getDb(applicationContext).getDao()
        val repository = Repository(dao)

        CoroutineScope(Dispatchers.IO).launch {
            repository.deletePlaces()
            repository.addPlaces(
                demoPlaces()
            )
        }

        sendLocalBroadcastInfo(context, Constants.LACTION_ADV_UPLOADED, "Uploaded new adventures.")

        return Result.success()
    }

    private fun demoPlaces(): List<Places> {
        val placesCount = Random.nextInt(1, 6)
        return List(placesCount) {
            val nameNum = Random.nextInt(1, 12)
            Places(null,
                "place $nameNum",
                "info $nameNum",
                "",
                "detail $nameNum"
            )
        }
    }


}