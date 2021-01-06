package com.udacity.asteroidradar.ui.main.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.data.repository.AsteroidRepository

class AsteroidWorkerFactory(private val repository: AsteroidRepository):WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return UpdateDataWorker(appContext,workerParameters,repository)
    }

}