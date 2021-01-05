package com.udacity.asteroidradar.data.injection.module

import android.app.Application
import android.content.Context
import com.udacity.asteroidradar.data.database.AsteroidDao
import com.udacity.asteroidradar.data.database.AsteroidDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDataBase(context: Context)=AsteroidDatabase.getInstance(context)

    @Provides
    @Singleton
    fun provideDao(asteroidDatabase: AsteroidDatabase):AsteroidDao = asteroidDatabase.asteroidDao()
}