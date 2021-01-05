package com.udacity.asteroidradar

import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.udacity.asteroidradar.util.ContextUtils
import java.util.*

class MainActivity : AppCompatActivity() {
lateinit var appBarConfiguration: AppBarConfiguration
    override fun attachBaseContext(newBase: Context) {
// get chosen language from shread preference
        val sharedPref = newBase.getSharedPreferences(
            "Settings", Context.MODE_PRIVATE
        )
        val language = sharedPref?.getString("Language", "en")
        val localeUpdatedContext: ContextWrapper = ContextUtils.updateLocale(newBase, Locale(language))
        super.attachBaseContext(localeUpdatedContext)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = this.findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController,appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return NavigationUI.navigateUp(navController,appBarConfiguration)
    }
}
