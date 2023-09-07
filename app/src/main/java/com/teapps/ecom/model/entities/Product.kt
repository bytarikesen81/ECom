package com.teapps.ecom.model.entities

import com.google.gson.annotations.SerializedName

class Product(
    /*Fields*/
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("title") var title: String?,
    @field:SerializedName(
        "description"
    ) var description: String?,
    @field:SerializedName("price") var price: Double,
    @field:SerializedName(
        "discountPercentage"
    ) var discountPercentage: Double,
    @field:SerializedName("rating") var rating: Double,
    @field:SerializedName(
        "stock"
    ) var stock: Int,
    @field:SerializedName("brand") var brand: String?,
    @field:SerializedName(
        "category"
    ) var category: String?,
    @field:SerializedName("thumbnail") var thumbnail: String?
) {

    @SerializedName("images")
    var images: List<String>? = null

    //Cart Fields
    @SerializedName("quantity")
    var quantity = 0

    @SerializedName("total")
    var total = 0

    @SerializedName("discountedPrice")
    var discountedPrice = 0

}