package com.example.travelapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelapp.R
import com.example.travelapp.adapters.CustomRecyclerAdapter
import com.example.travelapp.db.Db
import com.example.travelapp.db.Places
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.concurrent.LinkedBlockingQueue

class AdventureActivity : AppCompatActivity() {
    var db: Db? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adventure)

        db = Db.getDb(this)

        val recyclerView = findViewById<RecyclerView>(R.id.placesRecycler)
        recyclerView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL, false
        )
        recyclerView.adapter = CustomRecyclerAdapter(getAllPlaces())
    }

    fun addPlaceOnCLick(view: View) {
        val dialog = BottomSheetDialog(this)
        with (dialog) {
            setContentView(R.layout.places_dialog)
            findViewById<Button>(R.id.btnCancel)?.setOnClickListener { this.dismiss() }
            findViewById<Button>(R.id.btnDelete)?.setOnClickListener { clearAllPlaces(this) }
            findViewById<Button>(R.id.btnAdd)?.setOnClickListener { savePlace(this) }
            show()
        }
    }

    private fun savePlace(dialog: BottomSheetDialog) {
        val place = Places(
            name = dialog.findViewById<TextView>(R.id.editTextName)?.text.toString(),
            info = dialog.findViewById<TextView>(R.id.editTextInfo)?.text.toString(),
            textDetail = dialog.findViewById<TextView>(R.id.editTextDetail)?.text.toString(),
            imageUrl = ""
        )

        Thread {
            db!!.getDao().insertPlace(place)
        }.start()
        dialog.dismiss()
    }

    private fun clearAllPlaces(dialog: BottomSheetDialog) {
        Thread {
            db!!.getDao().deletePlaces()
        }.start()
        dialog.dismiss()
    }

    private fun getAllPlaces(): List<Places> {
        val queue = LinkedBlockingQueue<List<Places>>()
        Thread {
            queue.add(db!!.getDao().getAllPlaces())
        }.start()
        return queue.take()
    }
}