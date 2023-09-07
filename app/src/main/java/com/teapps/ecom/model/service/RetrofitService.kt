package com.teapps.ecom.model.service

import com.teapps.ecom.model.api.EComServerAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService private constructor(serverURL: String) {
    //DYNAMIC FIELDS
    //Object Fields
    private val server: Retrofit

    //API Instance
    val aPI: EComServerAPI

    //DYNAMIC METHODS
    //Instance Constructor
    init {
        server = Retrofit.Builder().baseUrl(serverURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        aPI = server.create(EComServerAPI::class.java)
    }

    companion object {
        //STATIC FIELDS
        //Instance
        private var service: RetrofitService? = null

        //STATIC METHODS
        fun getInstance(serviceURL: String): RetrofitService? {
            if (service == null) service = RetrofitService(serviceURL)
            return service
        }
    }
}