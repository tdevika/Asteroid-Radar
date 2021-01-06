package com.udacity.asteroidradar.ui.main

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.data.PictureOfDay
import com.udacity.asteroidradar.data.Results
import com.udacity.asteroidradar.data.repository.AsteroidRepository
import com.udacity.asteroidradar.ui.main.worker.WorkRequest
import com.udacity.asteroidradar.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: AsteroidRepository,
                                        private val workRequest: WorkRequest) : ViewModel() {
    val asteroids = MutableLiveData<List<Asteroid>>()
    val error = MutableLiveData<String>()
    val pictureUrl = MutableLiveData<PictureOfDay>()

    fun load() {
        workRequest.updateDataWorkRequest()
        setPictureOfDay()
        getAsteroidsFromToday()
    }

    private fun updateAsteroid() {
        viewModelScope.launch {
                repository.getAsteroidsFromServer()
                getAsteroidsFromToday()
        }
    }

    private fun setPictureOfDay() {
        viewModelScope.launch {
            repository.setPictureOfDay()
            getPictureOfDay()
        }
    }

    fun getPictureOfDay() {
        viewModelScope.launch {
            repository.getPictureOfDay().collectLatest {
               pictureUrl.value = it
            }
        }
    }

    fun getAsteroidsFromToday() {
        viewModelScope.launch {
            repository.getAsteroidsFromToday(getTodayDate).collectLatest {
                when (it) {
                    is Results.Success -> asteroids.value = it.value
                    is Results.Error -> error.value = it.message
                }
            }
        }
    }

    fun getTodayAsteroids() {
        viewModelScope.launch {
            repository.getToDayAsteroidFromDB(getTodayDate).collectLatest {
                when (it) {
                    is Results.Success -> asteroids.value = it.value
                    is Results.Error -> error.value = it.message
                }
            }
        }
    }

    fun getAllAsteroids() {
        viewModelScope.launch {
            repository.getAllAsteroidsFromDB().collectLatest {
                when (it) {
                    is Results.Success -> asteroids.value = it.value
                    is Results.Error -> error.value = it.message
                }
            }
        }
    }
}

@SuppressLint("SimpleDateFormat", "WeekBasedYear")
val getTodayDate =
    SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT).format(Calendar.getInstance().time)


