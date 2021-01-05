package com.udacity.asteroidradar.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.data.PictureOfDay

@Database(entities = [Asteroid::class,PictureOfDay::class], version = 2, exportSchema = false)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract fun asteroidDao(): AsteroidDao

    companion object {
        fun getInstance(context: Context): AsteroidDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AsteroidDatabase::class.java,
                "asteroid_database"
            ).fallbackToDestructiveMigration().build()
        }
    }
}