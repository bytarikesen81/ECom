package com.teapps.ecom.model.entities

import com.google.gson.annotations.SerializedName

class Login(
    /*Fields*/
    @field:SerializedName("username") var username: String,
    @field:SerializedName("password") var password: String
)