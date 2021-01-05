package com.udacity.asteroidradar.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.data.PictureOfDay
import kotlinx.coroutines.flow.Flow

@Dao
interface AsteroidDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setAsteroidInDB(asteroids:List<Asteroid>)

    @Query("SELECT * FROM asteroid_list where closeApproachDate=:todayDate")
    fun getToDayAsteroid(todayDate:String): Flow<List<Asteroid>>

    @Query("SELECT * FROM asteroid_list order by closeApproachDate ASC")
    fun getAllAsteroid(): Flow<List<Asteroid>>

    @Query("SELECT * FROM asteroid_list where closeApproachDate >=:todayDate order by closeApproachDate ASC")
    fun getAsteroidsFromToday(todayDate:String): Flow<List<Asteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setPictureOfDay(pictureOfDay: PictureOfDay)

    @Query("Select * from picture_table")
    fun getPictureOfDay():Flow<PictureOfDay>


}