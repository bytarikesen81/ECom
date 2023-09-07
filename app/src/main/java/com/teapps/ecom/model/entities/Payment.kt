package com.teapps.ecom.model.entities

import com.google.gson.annotations.SerializedName

class Payment {
    /*Fields*/
    //Dynamic Fields
    @SerializedName("cardExpire")
    var cardExpire: String? = null

    @SerializedName("cardNumber")
    var cardNumber: String? = null

    @SerializedName("cartType")
    var cardType: String? = null

    @SerializedName("currency")
    var currency: String? = null

    @SerializedName("iban")
    var iban: String? = null
}