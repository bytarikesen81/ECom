package com.teapps.ecom.model.entities

import com.google.gson.annotations.SerializedName

class UserBase {
    /*Fields*/
    @SerializedName("users")
    var users: List<User>? = null

    @SerializedName("total")
    var total = 0

    @SerializedName("skip")
    var skip = 0

    @SerializedName("limit")
    var limit = 0
}