package com.udacity.asteroidradar.data.repository

import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.data.PictureOfDay
import com.udacity.asteroidradar.data.Results
import com.udacity.asteroidradar.data.database.AsteroidDao
import com.udacity.asteroidradar.data.service.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject


class AsteroidRepository @Inject constructor(
    private val apiService: ApiService,
    private val asteroidDao: AsteroidDao
) {
   suspend fun getAsteroidsFromServer() {
        withContext(Dispatchers.IO) {
            try {
                val call = apiService.getAsteroidsFromServer()
                val response = call.execute()
                response.body()?.let {
                    val asteroids = parseAsteroidsJsonResult(JSONObject(it))
                    if (asteroids.isNotEmpty()) {
                        asteroidDao.setAsteroidInDB(asteroids)
                    } else {
                        asteroidDao.setAsteroidInDB(emptyList())
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

   suspend fun setPictureOfDay(){
        withContext(Dispatchers.IO){
            val pictureOfDay=apiService.getImageFromServer()
            asteroidDao.setPictureOfDay(pictureOfDay)
        }
    }
    suspend fun getPictureOfDay(): Flow<PictureOfDay> =
        withContext(Dispatchers.IO) {
            asteroidDao.getPictureOfDay()
        }


    suspend fun getToDayAsteroidFromDB(todayDate: String): Flow<Results<List<Asteroid>>> =
        withContext(Dispatchers.IO) {
            asteroidDao.getToDayAsteroid(todayDate)
                .onStart { Results.Loading }
                .catch { e -> e.message?.let { Results.Error(it) } }
                .map { Results.Success(it) }
        }

    suspend fun getAllAsteroidsFromDB(): Flow<Results<List<Asteroid>>> =
        withContext(Dispatchers.IO) {
            asteroidDao.getAllAsteroid()
                .onStart { Results.Loading }
                .catch { e -> e.message?.let { Results.Error(it) } }
                .map { Results.Success(it) }

        }

    suspend fun getAsteroidsFromToday(today :String): Flow<Results<List<Asteroid>>> =
        withContext(Dispatchers.IO) {
            asteroidDao.getAsteroidsFromToday(today)
                .onStart { Results.Loading }
                .catch { e -> e.message?.let { Results.Error(it) } }
                .map { Results.Success(it) }

        }
}


