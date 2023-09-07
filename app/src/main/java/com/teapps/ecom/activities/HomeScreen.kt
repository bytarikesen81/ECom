package com.teapps.ecom.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.teapps.ecom.R
import com.teapps.ecom.viewmodel.HomeViewModel

class HomeScreen : AppCompatActivity() {
    //View
    private var homeIbtnShop: ImageButton? = null
    private var homeIbtnProfile: ImageButton? = null
    private var homeIbtnCart: ImageButton? = null
    private var homeIbtnLogout: ImageButton? = null

    //ViewModel
    private var homeViewModel: HomeViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        initializeUIComponents()
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Quit Application")
        builder.setMessage("Do you want to quit from application? ")
        builder.setPositiveButton("Quit") { dialog, id -> finishAffinity() }
        builder.setNegativeButton("Cancel") { dialog, id -> dialog.cancel() }
        builder.show()
    }

    private fun initializeUIComponents() {
        homeIbtnShop = findViewById<View>(R.id.homeIbtnShop) as ImageButton
        homeIbtnShop!!.setOnClickListener {
            startActivity(
                Intent(
                    this@HomeScreen,
                    ShopScreen::class.java
                )
            )
        }
        homeIbtnProfile = findViewById<View>(R.id.homeIbtnProfile) as ImageButton
        homeIbtnProfile!!.setOnClickListener {
            startActivity(
                Intent(
                    this@HomeScreen,
                    ProfileScreen::class.java
                )
            )
        }
        homeIbtnCart = findViewById<View>(R.id.homeIbtnCart) as ImageButton
        homeIbtnCart!!.setOnClickListener {
            startActivity(
                Intent(
                    this@HomeScreen,
                    CartScreen::class.java
                )
            )
        }
        homeIbtnLogout = findViewById<View>(R.id.homeIbtnLogout) as ImageButton
        homeIbtnLogout!!.setOnClickListener { homeViewModel!!.logoutUser() }
    }
}