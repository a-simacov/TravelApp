package com.example.travelapp.workers

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.work.*
import java.util.*
import java.util.concurrent.TimeUnit

class WMLoader(private val context: Context) {

    private val wm = WorkManager.getInstance(context)

    fun loadData() {
        loadAdventures()
        loadTickets()
    }

    private fun loadAdventures() {
        loadAdventuresOneTime()
        loadAdventuresPeriodic()
    }

    private fun loadAdventuresOneTime() {
        val oneTimeRequest = OneTimeWorkRequestBuilder<LoadAdventures>()
            .setInitialDelay(15, TimeUnit.SECONDS)
            .addTag("loadAdventuresOneTime")
            .build()
        wm.enqueue(oneTimeRequest)
        checkStateById(oneTimeRequest.id)
    }

    private fun loadAdventuresPeriodic() {
        val tag = "loadAdventuresPeriodic"
        val name = "loadAdventures"
        val periodicRequest = PeriodicWorkRequestBuilder<LoadAdventures>(15, TimeUnit.MINUTES)
            .setConstraints(
                Constraints.Builder()
                    .setRequiresBatteryNotLow(true)
                    .build()
            )
            .addTag(tag)
            .build()
        wm.enqueueUniquePeriodicWork(
            name,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicRequest
        )
        checkStateByTag(tag)
        checkStateByName(name)
    }

    private fun loadTickets() {
        loadTicketsOneTime()
        //loadTicketsPeriodic()
    }

    private fun loadTicketsOneTime() {
        val data = Data.Builder()
            .putString("cityFrom", "Moscow")
            .putString("cityTo", "Omsk")
            .build()

        val oneTimeRequest = OneTimeWorkRequestBuilder<LoadTickets>()
            .setInputData(data)
            .setInitialDelay(1, TimeUnit.SECONDS)
            .addTag("loadTicketsOneTime")
            .build()
        wm.enqueue(oneTimeRequest)
        checkStateById(oneTimeRequest.id)
    }

    private fun loadTicketsPeriodic() {
        val tag = "loadTicketsPeriodic"
        val name = "loadTickets"

        val periodicRequest = PeriodicWorkRequestBuilder<LoadTickets>(16, TimeUnit.MINUTES)
            .setConstraints(
                Constraints.Builder()
                    .setRequiresBatteryNotLow(true)
                    .build()
            )
            .addTag(tag)
            .build()
        wm.enqueueUniquePeriodicWork(
            name,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicRequest
        )
        checkStateByTag(tag)
        checkStateByName(name)
    }

    private fun checkStateById(id: UUID) {
        wm.getWorkInfoByIdLiveData(id).observe(context as LifecycleOwner) {
            Log.i("workState", "Work state $id: ${it.state}")
        }
    }

    private fun checkStateByTag(tag: String) {
        wm.getWorkInfosByTagLiveData(tag).observe(context as LifecycleOwner) {
            it.forEach {
                Log.i("workState", "Work state tag: $tag ${it.id}: ${it.state}")
            }
        }
    }

    private fun checkStateByName(name: String) {
        wm.getWorkInfosForUniqueWorkLiveData(name).observe(context as LifecycleOwner) {
            it.forEach {
                Log.i("workState", "Work state name: $name ${it.id}: ${it.state}")
            }
        }
    }
}