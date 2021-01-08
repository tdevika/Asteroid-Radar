package com.udacity.asteroidradar

import android.app.Application
import androidx.work.Configuration
import com.udacity.asteroidradar.data.injection.component.AppComponent
import com.udacity.asteroidradar.data.injection.component.DaggerAppComponent
import javax.inject.Inject

class AsteroidApplication : Application(), Configuration.Provider {

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

