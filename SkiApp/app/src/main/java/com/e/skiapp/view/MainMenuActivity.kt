package com.e.skiapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.e.skiapp.R
import com.e.skiapp.databinding.ActivityMainMenuBinding

class MainMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainMenuBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_menu) as ActivityMainMenuBinding

        val drawerLayout = binding.mainMenuDrawerLayout

        binding.root.findViewById<ImageView>(R.id.imageMenu).setOnClickListener() {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        val navigationView = binding.navigationView

        val navController = Navigation.findNavController(this, R.id.navHostFragment)
        NavigationUI.setupWithNavController(navigationView, navController)
        val text = binding.root.findViewById<TextView>(R.id.textMenu)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            text.text = when (destination.id) {
                R.id.HomeFragment -> "Home"
                R.id.AccountFragment -> "Manage account"
                R.id.elevatorFragment -> "Manage elevators"
                R.id.mapFragment -> "Map"
                else -> "SkiApp"
            }
        }

    }
}