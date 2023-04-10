package com.example.travelapp.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.travelapp.db.Db
import com.example.travelapp.db.Repository
import com.example.travelapp.db.Ticket
import com.example.travelapp.tools.Constants
import com.example.travelapp.tools.sendLocalBroadcastInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

class LoadTickets(val context: Context, workingParameters: WorkerParameters) : Worker(context, workingParameters) {

    override fun doWork(): Result {
        val dao = Db.getDb(applicationContext).getDao()
        val repository = Repository(dao)

//        val cityFrom = inputData.getString("cityFrom") ?: "demo city"
//        val cityTo = inputData.getString("cityTo") ?: "demo city"
        val citiesFrom = arrayOf("Moscow", "Omsk", "Ekaterinburg")
        val citiesTo = arrayOf("Madrid", "Chisinau", "Pekin")

        CoroutineScope(Dispatchers.Unconfined).launch {
            repository.deleteTickets()
            repository.addTickets(
                demoTickets(citiesFrom, citiesTo)
            )
        }

        sendLocalBroadcastInfo(context, Constants.LACTION_TICKETS_UPLOADED, "Uploaded new tickets.")

        return Result.success()
    }

    private fun demoTickets(citiesFrom: Array<String>, citiesTo: Array<String>): List<Ticket> {
        val ticketsCount = 5
        return List(ticketsCount) {
            val date = String.format("2023-%02d-%02d",
                Random.nextInt(4, 12),
                Random.nextInt(11, 30)
            )
            val airline = if (Random.nextInt(1, 3) == 1) "Aeroflot" else "S7"
            Ticket(null, citiesFrom.random(), citiesTo.random(), date, date, airline, "")
        }
    }

}