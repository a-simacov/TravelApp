package com.example.travelapp.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.travelapp.db.Db
import com.example.travelapp.db.Repository
import com.example.travelapp.db.Ticket
import kotlin.random.Random

class LoadTickets(context: Context, workingParameters: WorkerParameters) : Worker(context, workingParameters) {
    override fun doWork(): Result {
        val dao = Db.getDb(applicationContext).getDao()
        val repository = Repository(dao)

        val cityFrom = inputData.getString("cityFrom") ?: "demo city"
        val cityTo = inputData.getString("cityTo") ?: "demo city"

        repository.deleteTickets()
        repository.addTickets(
            demoTickets(cityFrom, cityTo)
        )
        return Result.success()
    }

    private fun demoTickets(cityFrom: String, cityTo: String): List<Ticket> {
        val ticketsCount = Random.nextInt(1, 11)
        println("BD updated: $ticketsCount")
        return List(ticketsCount) {
            val date = "2023-${Random.nextInt(1, 12)}-${Random.nextInt(1, 20)}"
            val airline = if (Random.nextInt(1, 3) == 1) "Aeroflot" else "S7"
            Ticket(null, cityFrom, cityTo, date, date, airline, "")
        }
    }
}