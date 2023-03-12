package com.example.travelapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Places::class], version = 1)
abstract class Db: RoomDatabase() {
    abstract fun getDao(): Dao

    companion object {
        fun getDb(context: Context): Db {
            return Room.databaseBuilder(
                context.applicationContext,
                Db::class.java,
                "travel.db"
            ).fallbackToDestructiveMigration().build()
        }
    }
}