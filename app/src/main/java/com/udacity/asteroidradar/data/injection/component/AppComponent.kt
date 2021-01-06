package com.udacity.asteroidradar.data.injection.component

import android.content.Context
import com.udacity.asteroidradar.AsteroidApplication
import com.udacity.asteroidradar.data.injection.module.AppModule
import com.udacity.asteroidradar.data.injection.module.DatabaseModule
import com.udacity.asteroidradar.data.injection.module.NetWorkModule
import com.udacity.asteroidradar.data.injection.module.ViewModelModule
import com.udacity.asteroidradar.ui.main.MainFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [ViewModelModule::class,NetWorkModule::class,DatabaseModule::class,AppModule::class])
interface AppComponent{
    fun inject(mainFragment: MainFragment)
    fun inject(mainFragment: AsteroidApplication)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context
        ): AppComponent
    }
}