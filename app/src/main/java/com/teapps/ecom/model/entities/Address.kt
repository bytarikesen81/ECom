package com.teapps.ecom.model.entities

import com.google.gson.annotations.SerializedName

class Address {
    @SerializedName("address")
    var address: String? = null

    @SerializedName("city")
    var city: String? = null

    @SerializedName("coordinates")
    var coordinates: Coordinates? = null

    @SerializedName("postalCode")
    var postalCode: String? = null

    @SerializedName("state")
    var state: String? = null

    /**Inner Classes */
    inner class Coordinates {
        @SerializedName("lat")
        var lat: Double? = null

        @SerializedName("lon")
        var lon: Double? = null
    }
}