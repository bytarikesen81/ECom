package com.teapps.ecom.model.api

import com.teapps.ecom.model.entities.Cart
import com.teapps.ecom.model.entities.CartBase
import com.teapps.ecom.model.entities.Login
import com.teapps.ecom.model.entities.Product
import com.teapps.ecom.model.entities.Shop
import com.teapps.ecom.model.entities.User
import com.teapps.ecom.model.entities.UserBase
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface EComServerAPI {
    /*PRODUCT OPERATIONS*/ //Get Active Shop Content and All Products
    @get:GET("products")
    val shop: Call<Shop?>?

    //Get a Product
    @GET("products/{id}")
    fun getProduct(@Path("id") id: Int): Call<Product?>?

    //Search a Product by Keyword
    @GET("/products/search")
    fun searchShopByKeyword(@Query("q") q: String?): Call<Shop?>?

    //Search Products by Category
    @GET("/products/category/{categoryTag}")
    fun searchShopByCategory(@Path("categoryTag") categoryTag: String?): Call<Shop?>?

    /*USER OPERATIONS*/
    //Get Active UserList
    @get:GET("users")
    val users: Call<UserBase?>?

    //Get User
    @GET("users/{id}")
    fun getUser(@Path("id") id: Int): Call<User?>?

    //Ensure Login Operation
    @POST("/auth/login")
    fun loginUser(@Body login: Login?): Call<User?>?

    /*CART OPERATIONS*/ //Get a Cart
    @GET("carts/{id}")
    fun getCart(@Path("id") id: Int): Call<Cart?>?

    //Get User CartList
    @GET("users/{id}/carts")
    fun getUserCarts(@Path("id") id: Int): Call<CartBase?>?
}