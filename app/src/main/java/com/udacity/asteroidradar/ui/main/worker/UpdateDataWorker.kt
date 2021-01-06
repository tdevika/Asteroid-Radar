package com.udacity.asteroidradar.ui.main.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.data.repository.AsteroidRepository
import retrofit2.HttpException

class UpdateDataWorker(
    appContext: Context,
    params: WorkerParameters,
    val repository:AsteroidRepository
) :
    CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        return try {
            repository.getAsteroidsFromServer()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}