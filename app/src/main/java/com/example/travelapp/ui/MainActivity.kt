package com.example.travelapp.ui

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.work.*
import com.example.travelapp.databinding.ActivityMainBinding
import com.example.travelapp.receivers.BCReceiverAdventuresUploaded
import com.example.travelapp.receivers.BCReceiverAirplane
import com.example.travelapp.receivers.BCReceiverBluetooth
import com.example.travelapp.receivers.BCReceiverTicketsUploaded
import com.example.travelapp.tools.Constants
import com.example.travelapp.ui.home.HomeActivity
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

        registerBCReceivers()
    }

    private fun registerBCReceivers() {
        registerReceiver(
            BCReceiverBluetooth(),
            IntentFilter(Constants.ACTION_BT_STATE_CHANGED),
            android.Manifest.permission.BLUETOOTH,
            null
        )

        registerReceiver(
            BCReceiverAirplane(),
            IntentFilter(Constants.ACTION_AIRPLANE_MODE)
        )

        LocalBroadcastManager.getInstance(this).also { bcManager ->
            bcManager.registerReceiver(
                BCReceiverAdventuresUploaded(),
                IntentFilter(Constants.LACTION_ADV_UPLOADED)
            )
            bcManager.registerReceiver(
                BCReceiverTicketsUploaded(),
                IntentFilter(Constants.LACTION_TICKETS_UPLOADED)
            )
        }
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
            .addTag(Constants.TAG_WI_LOAD_ADVS_SINGLE)
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
        loadTicketsPeriodic()
    }

    private fun loadTicketsOneTime() {
        val data = Data.Builder()
            .putString("cityFrom", "Moscow")
            .putString("cityTo", "Omsk")
            .build()

        val oneTimeRequest = OneTimeWorkRequestBuilder<LoadTickets>()
            .setInputData(data)
            .setInitialDelay(10, TimeUnit.SECONDS)
            .addTag(Constants.TAG_WI_LOAD_TICKETS_SINGLE)
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
            Log.i("workState", "Work state $id: ${it.state}")
        }
    }

    private fun checkStateByTag(tag: String) {
        wm.getWorkInfosByTagLiveData(tag).observe(this) {
            it.forEach {
                Log.i("workState", "Work state tag: $tag ${it.id}: ${it.state}")
            }
        }
    }

    private fun checkStateByName(name: String) {
        wm.getWorkInfosForUniqueWorkLiveData(name).observe(this) {
            it.forEach {
                Log.i("workState", "Work state name: $name ${it.id}: ${it.state}")
            }
        }
    }

}