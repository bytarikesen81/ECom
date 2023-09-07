package com.teapps.ecom.viewmodel

import android.app.Application
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.teapps.ecom.activities.HomeScreen
import com.teapps.ecom.model.api.UIThreadHandler
import com.teapps.ecom.model.entities.Login
import com.teapps.ecom.model.entities.User
import com.teapps.ecom.model.repository.DummyJsonRepository
import com.teapps.ecom.model.repository.DummyJsonRepository.ILogOperationResponse

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    /**Repositories */ /*Repo 1*/
    private val repo //Repo Object
            : DummyJsonRepository?
    //Model data from Repo 1
    /**Constructor */
    init {
        repo = DummyJsonRepository.Companion.instance
    }

    /**Business Logic Methods */
    fun getUserToLogin(loginCredentials: Login) {
        val activityContext = getApplication<Application>().applicationContext
        val uiThreadHandler = UIThreadHandler(activityContext)
        repo!!.loginUser(loginCredentials, activityContext, object : ILogOperationResponse {
            override fun onResponse(user: User?) {
                repo.updateCurrentUser(user!!.id)
                if (DummyJsonRepository.Companion.instance!!.currentUser != null) {
                    User.Companion.currentUser = (user)
                    val loginToHome = Intent(activityContext, HomeScreen::class.java)
                    loginToHome.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    getApplication<Application>().startActivity(loginToHome)
                }
            }

            override fun onFailure(t: Throwable) {
                uiThreadHandler.dispatch {
                    Toast.makeText(
                        activityContext,
                        "Login failed:" + t.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}