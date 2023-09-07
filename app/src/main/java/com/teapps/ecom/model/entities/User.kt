package com.teapps.ecom.model.entities

import com.google.gson.annotations.SerializedName

class User {
    /**Fields */
    /*Dynamic Fields*/
    //Standard Fields
    @SerializedName("id")
    var id = 0

    @SerializedName("username")
    var username: String? = null

    @SerializedName("email")
    var email: String? = null

    @SerializedName("firstName")
    var firstName: String? = null

    @SerializedName("lastName")
    var lastName: String? = null

    @SerializedName("gender")
    var gender: String? = null

    @SerializedName("image")
    var image: String? = null

    @SerializedName("token")
    var token: String? = null

    //Password
    var password: String? = null

    //Extra Fields
    @SerializedName("phone")
    var phone: String? = null

    @SerializedName("birthDate")
    var birthDate: String? = null

    //Technical Fields
    @SerializedName("domain")
    var domain: String? = null

    @SerializedName("ip")
    var ip: String? = null

    //Address Field
    @SerializedName("address")
    var address: Address? = null

    //Payment Field
    @SerializedName("bank")
    var bank: Payment? = null

    //User Carts
    var userCarts: CartBase? = null

    companion object {
        /*Static Fields*/
        var currentUser //Current User Instance
                : User? = null

        fun logoutUser() {
            currentUser = null
        }
    }
}