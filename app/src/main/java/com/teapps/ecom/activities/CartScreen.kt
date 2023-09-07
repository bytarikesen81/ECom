package com.teapps.ecom.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.teapps.ecom.R
import com.teapps.ecom.databinding.ActivityCartScreen2Binding

class CartScreen : AppCompatActivity() {
    //View
    private var binding: ActivityCartScreen2Binding? = null
    private var navView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartScreen2Binding.inflate(layoutInflater)
        setContentView(binding!!.root)
        navView = findViewById(R.id.nav_view)
        val navController = findNavController(this, R.id.nav_host_fragment_activity_cart_screen2)
        setupWithNavController(binding!!.navView, navController)
    }
}