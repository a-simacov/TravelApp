package com.example.travelapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.work.*
import com.example.travelapp.databinding.ActivityMainBinding
import com.example.travelapp.workers.LoadAdventures
import com.example.travelapp.workers.LoadTickets
import java.util.UUID
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val wm = WorkManager.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initOnClickListeners()
        loadData()
    }

    private fun initOnClickListeners() {
        binding.skipText.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }

    private fun loadData() {
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
            .setInitialDelay(10, TimeUnit.SECONDS)
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
        wm.getWorkInfoByIdLiveData(id).observe(this) {
            Log.i("workState", "Work state by id $id: ${it.state}")
        }
    }

    private fun checkStateByTag(tag: String) {
        wm.getWorkInfosByTagLiveData(tag).observe(this) { workInfos ->
            workInfos.forEach {
                Log.i("workState", "Work state by tag: $tag ${it.id}: ${it.state}")
            }
        }
    }

    private fun checkStateByName(name: String) {
        wm.getWorkInfosForUniqueWorkLiveData(name).observe(this) { workInfos ->
            workInfos.forEach {
                Log.i("workState", "Work state by name: $name ${it.id}: ${it.state}")
            }
        }
    }

}