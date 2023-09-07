package com.teapps.ecom.model.entities

import com.google.gson.annotations.SerializedName

class CartBase {
    /*Fields*/
    @SerializedName("carts")
    var carts: ArrayList<Cart>? = null

    @SerializedName("total")
    var total = 0

    @SerializedName("skip")
    var skip = 0

    @SerializedName("limit")
    var limit = 0
}