package com.udacity.asteroidradar

import android.app.Application
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.udacity.asteroidradar.data.injection.component.AppComponent
import com.udacity.asteroidradar.data.injection.component.DaggerAppComponent
import com.udacity.asteroidradar.ui.main.worker.UpdateDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Appendable
import javax.inject.Inject

class AsteroidApplication:Application() ,Configuration.Provider {

    @Inject
    lateinit var workerConfiguration: Configuration

    // Instance of the AppComponent that will be used by all the Activities in the project
    val daggerAppComponent: AppComponent by lazy {
        initializeComponent()
    }

    private fun initializeComponent(): AppComponent {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
        return DaggerAppComponent.factory().create(applicationContext)
    }
    override fun onCreate() {
        super.onCreate()
        daggerAppComponent.inject(this)
    }


    override fun getWorkManagerConfiguration(): Configuration {
        return workerConfiguration
    }
}

