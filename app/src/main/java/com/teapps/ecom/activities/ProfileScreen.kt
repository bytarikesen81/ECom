package com.teapps.ecom.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.teapps.ecom.R
import com.teapps.ecom.model.entities.User
import com.teapps.ecom.model.repository.DummyJsonRepository

class ProfileScreen : AppCompatActivity() {
    //View
    private var profileTxtFieldName: EditText? = null
    private var profileTxtFieldSurname: EditText? = null
    private var profileTxtFieldEmail: EditText? = null
    private var profileTxtFieldPhone: EditText? = null
    private var profileTxtFieldBirthdate: EditText? = null
    private var profileTxtFieldOldPass: EditText? = null
    private var profileTxtFieldNewPass: EditText? = null
    private var profileSpinnerGender: Spinner? = null
    private var profileBtnChangePass: Button? = null
    private var profileBtnBack: Button? = null
    private var profileBtnSubmit: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_screen)
        initializeUIComponents()
    }

    private fun initializeUIComponents() {
        val currentUser: User? = DummyJsonRepository.Companion.instance!!.currentUser
        profileTxtFieldName = findViewById<View>(R.id.profileTxtFieldName) as EditText
        profileTxtFieldName!!.setText("" + currentUser!!.firstName)
        profileTxtFieldSurname = findViewById<View>(R.id.profileTxtFieldSurname) as EditText
        profileTxtFieldSurname!!.setText("" + currentUser!!.lastName)
        profileTxtFieldEmail = findViewById<View>(R.id.profileTxtFieldEmail) as EditText
        profileTxtFieldEmail!!.setText(currentUser!!.email)
        profileTxtFieldPhone = findViewById<View>(R.id.profileTxtFieldPhone) as EditText
        profileTxtFieldPhone!!.setText(currentUser!!.phone)
        profileTxtFieldBirthdate = findViewById<View>(R.id.profileTxtFieldBirthdate) as EditText
        profileTxtFieldBirthdate!!.setText(currentUser.birthDate)
        profileSpinnerGender = findViewById<View>(R.id.profileSpinnerGender) as Spinner
        when (currentUser.gender) {
            "male" -> profileSpinnerGender!!.setSelection(0)
            "female" -> profileSpinnerGender!!.setSelection(1)
            else -> {}
        }
        profileTxtFieldOldPass = findViewById<View>(R.id.profileTxtFieldOldPass) as EditText
        profileTxtFieldNewPass = findViewById<View>(R.id.profileTxtFieldNewPass) as EditText
        profileBtnChangePass = findViewById<View>(R.id.profileBtnChangePass) as Button
        profileBtnChangePass!!.setOnClickListener { changePassword() }
        profileBtnSubmit = findViewById<View>(R.id.profileBtnSubmit) as Button
        profileBtnSubmit!!.setOnClickListener { changeProfileInfo() }
        profileBtnBack = findViewById<View>(R.id.profileBtnBack) as Button
        profileBtnBack!!.setOnClickListener {
            startActivity(
                Intent(
                    this@ProfileScreen,
                    HomeScreen::class.java
                )
            )
        }
    }

    private fun changeProfileInfo() {
        if (profileTxtFieldName!!.text.toString()
                .matches(Regex("")) || profileTxtFieldSurname!!.text.toString()
                .matches(Regex("")) || profileTxtFieldEmail!!.text.toString().matches(Regex("")) ||
            profileTxtFieldPhone!!.text.toString()
                .matches(Regex("")) || profileTxtFieldBirthdate!!.text.toString().matches(Regex(""))
        ) {
            Toast.makeText(
                this@ProfileScreen,
                "User info fields cannot be empty",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            DummyJsonRepository.Companion.instance!!.currentUser!!.firstName =
                profileTxtFieldName!!.text.toString()

            DummyJsonRepository.Companion.instance!!.currentUser!!.lastName =
                profileTxtFieldSurname!!.text.toString()

            DummyJsonRepository.Companion.instance!!.currentUser!!.email =
                profileTxtFieldEmail!!.text.toString()

            DummyJsonRepository.Companion.instance!!.currentUser!!.phone =
                profileTxtFieldPhone!!.text.toString()

            DummyJsonRepository.Companion.instance!!.currentUser!!.phone =
                profileTxtFieldBirthdate!!.text.toString()

            DummyJsonRepository.Companion.instance!!.currentUser!!.gender =
                profileSpinnerGender!!.selectedItem.toString()

            Toast.makeText(this@ProfileScreen, "User info changed successfully", Toast.LENGTH_LONG)
                .show()
            startActivity(Intent(this@ProfileScreen, HomeScreen::class.java))
        }
    }

    private fun changePassword() {
        val oldPassword = DummyJsonRepository.Companion.instance!!.currentUser!!.password
        if (!profileTxtFieldOldPass!!.text.toString()
                .matches(Regex(""+oldPassword))
        ) {
            Toast.makeText(this@ProfileScreen, "User password is invalid", Toast.LENGTH_LONG).show()
        } else {
            DummyJsonRepository.Companion.instance!!.currentUser!!.password =
                profileTxtFieldNewPass!!.text.toString()

            Toast.makeText(
                this@ProfileScreen,
                "User password changed successfully",
                Toast.LENGTH_LONG
            ).show()
            startActivity(Intent(this@ProfileScreen, HomeScreen::class.java))
        }
    }
}