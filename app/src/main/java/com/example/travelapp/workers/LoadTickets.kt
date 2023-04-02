package com.example.travelapp.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.travelapp.db.Db
import com.example.travelapp.db.Repository
import com.example.travelapp.db.Ticket
import com.example.travelapp.tools.sendLocalBroadcastInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

class LoadTickets(val context: Context, workingParameters: WorkerParameters) : Worker(context, workingParameters) {

    private val successAction = "com.example.travelapp.uploadedNewTickets"

    override fun doWork(): Result {
        val dao = Db.getDb(applicationContext).getDao()
        val repository = Repository(dao)

        val cityFrom = inputData.getString("cityFrom") ?: "demo city"
        val cityTo = inputData.getString("cityTo") ?: "demo city"

        CoroutineScope(Dispatchers.Unconfined).launch {
            repository.deleteTickets()
            repository.addTickets(
                demoTickets(cityFrom, cityTo)
            )
        }

        sendLocalBroadcastInfo(context, successAction, "Uploaded new tickets.")

        return Result.success()
    }

    private fun demoTickets(cityFrom: String, cityTo: String): List<Ticket> {
        val ticketsCount = 5
        return List(ticketsCount) {
            val date = "2023-${Random.nextInt(1, 12)}-${Random.nextInt(1, 20)}"
            val airline = if (Random.nextInt(1, 3) == 1) "Aeroflot" else "S7"
            Ticket(null, cityFrom, cityTo, date, date, airline, "")
        }
    }

}