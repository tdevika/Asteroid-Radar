package com.udacity.asteroidradar.ui.main.worker

import androidx.work.DelegatingWorkerFactory
import com.udacity.asteroidradar.data.repository.AsteroidRepository
import javax.inject.Inject

class AsteroidDelegatingWorkerFactory @Inject constructor( repository: AsteroidRepository):DelegatingWorkerFactory() {
    init {
        addFactory(AsteroidWorkerFactory(repository))
    }
}