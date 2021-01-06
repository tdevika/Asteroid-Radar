package com.udacity.asteroidradar.data.injection.module

import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkManager
import com.udacity.asteroidradar.ui.main.worker.AsteroidDelegatingWorkerFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideWorkManager(
        context: Context
    ): WorkManager = WorkManager.getInstance(context)

    @Singleton
    @Provides
    fun provideWorkManagerConfiguration(
        asteroidDelegatingFactory: AsteroidDelegatingWorkerFactory
    ): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .setWorkerFactory(asteroidDelegatingFactory)
            .build()
    }
}