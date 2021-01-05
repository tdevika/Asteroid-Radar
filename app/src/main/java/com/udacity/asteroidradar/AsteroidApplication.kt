package com.udacity.asteroidradar

import android.app.Application
import com.udacity.asteroidradar.data.injection.component.AppComponent
import com.udacity.asteroidradar.data.injection.component.DaggerAppComponent
import java.lang.Appendable

class AsteroidApplication:Application() {

    // Instance of the AppComponent that will be used by all the Activities in the project
    val daggerAppComponent: AppComponent by lazy {
        initializeComponent()
    }

    fun initializeComponent(): AppComponent {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
        return DaggerAppComponent.factory().create(applicationContext)
    }
}