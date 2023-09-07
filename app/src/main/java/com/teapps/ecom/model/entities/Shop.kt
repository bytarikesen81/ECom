package com.teapps.ecom.model.entities

import com.google.gson.annotations.SerializedName

class Shop {
    /*Fields*/
    @SerializedName("products")
    var products: MutableList<Product?>? = null

    @SerializedName("total")
    var total = 0

    @SerializedName("skip")
    var skip = 0

    @SerializedName("limit")
    var limit = 0
}