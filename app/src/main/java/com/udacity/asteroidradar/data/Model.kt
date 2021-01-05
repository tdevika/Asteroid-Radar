package com.udacity.asteroidradar.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "picture_table")
data class PictureOfDay(val media_type: String, val title: String,
                        @PrimaryKey val url: String)

@Parcelize
@Entity(tableName = "asteroid_list")
data class Asteroid(@PrimaryKey val id: Long, val codename: String, val closeApproachDate: String,
                    val absoluteMagnitude: Double, val estimatedDiameter: Double,
                    val relativeVelocity: Double, val distanceFromEarth: Double,
                    val isPotentiallyHazardous: Boolean) : Parcelable


sealed class Results<out T>{
    data class Success<out T>(val value:T):Results<T>()
    data class Error(val message:String):Results<Nothing>()
    object Loading:Results<Nothing>()
}