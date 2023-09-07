package com.teapps.ecom.model.entities

import com.google.gson.annotations.SerializedName

class Cart(
    /*Fields*/
    @field:SerializedName("id") var cartID: Int, userId: Int
) {

    @SerializedName("products")
    var products: ArrayList<Product?>

    @SerializedName("total")
    var total: Int

    @SerializedName("discountedTotal")
    var discountedTotal: Int

    @SerializedName("userId")
    var userId: Int

    @SerializedName("totalProducts")
    var totalProducts: Int

    @SerializedName("totalQuantity")
    var totalQuantity: Int

    /*Methods*/
    //Constructor
    init {
        products = ArrayList()
        total = 0
        discountedTotal = 0
        this.userId = userId
        totalProducts = 0
        totalQuantity = 0
    }
}