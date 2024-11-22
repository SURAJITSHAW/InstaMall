package com.example.instamall.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.instamall.R
import com.example.instamall.databinding.ActivityShoppingBinding

class ShoppingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShoppingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle system insets (padding for status/navigation bars)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Apply padding for the main layout but not for BottomNavigationView
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0) // No bottom padding here
            insets
        }

        val navHostFragment = supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
        val navController = navHostFragment.navController

        // Setup BottomNavigationView with Navigation Controller
        binding.bottomNavView.setupWithNavController(navController)
    }
}
