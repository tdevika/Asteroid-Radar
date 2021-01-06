package com.udacity.asteroidradar.data.injection.module

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.Configuration
import androidx.work.WorkManager
import com.udacity.asteroidradar.data.injection.scope.ViewModelScope
import com.udacity.asteroidradar.ui.main.MainViewModel
import com.udacity.asteroidradar.ui.main.worker.AsteroidDelegatingWorkerFactory
import com.udacity.asteroidradar.util.AsteroidViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(asteroidViewModelFactory: AsteroidViewModelFactory):ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelScope(MainViewModel::class)
    abstract fun addMainViewModel(mainViewModel: MainViewModel):ViewModel
}
