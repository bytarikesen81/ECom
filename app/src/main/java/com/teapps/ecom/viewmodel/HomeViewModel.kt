package com.teapps.ecom.viewmodel

import android.app.Application
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.teapps.ecom.activities.LoginScreen
import com.teapps.ecom.model.repository.DummyJsonRepository

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    /**Repositories */ /*Repo 1*/
    private val repo //Repo Object
            : DummyJsonRepository? = DummyJsonRepository.Companion.instance
    //Model data from Repo 1

    /**Business Logic Methods */
    fun logoutUser() {
        val activityContext = getApplication<Application>().applicationContext
        repo!!.logoutUser()
        if (DummyJsonRepository.Companion!!.instance!!.currentUser == null) {
            val logoutBack = Intent(activityContext, LoginScreen::class.java)
            logoutBack.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            getApplication<Application>().startActivity(logoutBack)
        } else Toast.makeText(activityContext, "Logout Failed", Toast.LENGTH_LONG).show()
    }
}