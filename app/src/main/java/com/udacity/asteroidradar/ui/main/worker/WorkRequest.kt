package com.udacity.asteroidradar.ui.main.worker

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import javax.inject.Inject

class WorkRequest @Inject constructor(private val workManager: WorkManager) {
     fun updateDataWorkRequest(){
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val oneTimeWorkRequest = OneTimeWorkRequest
            .Builder(UpdateDataWorker::class.java)
            .setConstraints(constraints)//i added constraints
            .build()

        workManager.enqueue(oneTimeWorkRequest)
    }
}
