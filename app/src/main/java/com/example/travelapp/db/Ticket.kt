package com.example.travelapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tickets")
data class Ticket (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "cityFrom")
    var cityFrom: String,
    @ColumnInfo(name = "cityTo")
    var cityTo: String,
    @ColumnInfo(name = "departureDate")
    var departureDate: String,
    @ColumnInfo(name = "arrivalDate")
    var arrivalDate: String,
    @ColumnInfo(name = "airline")
    var airline: String
)