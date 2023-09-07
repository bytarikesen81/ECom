package com.teapps.ecom.activities

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.teapps.ecom.R
import com.teapps.ecom.model.entities.Login
import com.teapps.ecom.viewmodel.LoginViewModel

class LoginScreen : AppCompatActivity() {
    //View
    private var lgnTxtUsrname: EditText? = null
    private var lgnTxtPass: EditText? = null
    private var lgnSubmit: Button? = null

    //ViewModel
    private var loginViewModel: LoginViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        initializeUIComponents()
    }

    override fun onDestroy() {
        super.onDestroy()
        finish()
    }

    private fun initializeUIComponents() {
        lgnTxtUsrname = findViewById<View>(R.id.lgnTxtUsrname) as EditText
        lgnTxtPass = findViewById<View>(R.id.lgnTxtPass) as EditText
        lgnSubmit = findViewById<View>(R.id.lgnSubmit) as Button
        lgnSubmit!!.setOnClickListener {
            loginViewModel!!.getUserToLogin(
                Login(
                    lgnTxtUsrname!!.text.toString(),
                    lgnTxtPass!!.text.toString()
                )
            )
        }
    }
}