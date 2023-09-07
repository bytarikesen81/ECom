package com.teapps.ecom.model.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.teapps.ecom.model.api.EComServerAPI
import com.teapps.ecom.model.entities.Cart
import com.teapps.ecom.model.entities.CartBase
import com.teapps.ecom.model.entities.Login
import com.teapps.ecom.model.entities.Product
import com.teapps.ecom.model.entities.Shop
import com.teapps.ecom.model.entities.User
import com.teapps.ecom.model.service.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import kotlin.math.roundToInt

class DummyJsonRepository private constructor() {
    //DYNAMIC FIELDS
    private val serverAPI: EComServerAPI

    private val shopContent: MutableLiveData<Shop?>
    var cartContent: MutableLiveData<Cart?>?
        private set

    var currentUser: User? = null
        private set
    private var totalCartCount: Int

    //DYNAMIC METHODS
    //Instance Constructor
    init {
        serverAPI = RetrofitService.Companion.getInstance(SERVER_URL)!!.aPI
        shopContent = MutableLiveData()
        cartContent = MutableLiveData()
        totalCartCount = 20
    }

    //Instance Methods
    //USER OPERATIONS
    fun loginUser(
        loginCredentials: Login,
        context: Context?,
        loginResponse: ILogOperationResponse
    ) {
        val call = serverAPI.loginUser(loginCredentials)
        call!!.enqueue(object : Callback<User?> {
            override fun onResponse(call: Call<User?>, response: Response<User?>) {
                if (!response.isSuccessful) {
                    loginResponse.onFailure(Throwable(response.message()))
                } else {
                    currentUser = response.body()
                    currentUser!!.password = (loginCredentials.password)
                    usersCart
                    loginResponse.onResponse(currentUser)
                }
            }

            override fun onFailure(call: Call<User?>, t: Throwable) {
                loginResponse.onFailure(t)
            }
        })
    }

    fun logoutUser(): User? {
        var userToLogout: User? = null
        if (currentUser != null) {
            userToLogout = currentUser
            currentUser = null
            deleteUsersCart()
        }
        return userToLogout
    }

    fun updateCurrentUser(userID: Int): User? {
        val callUserDetailedInfo = serverAPI.getUser(userID)
        val callUserCartList = serverAPI.getUserCarts(userID)
        val userUpdateThread = Thread {
            var userResponse: Response<User?>? = null
            var userCartListResponse: Response<CartBase?>? = null
            try {
                userResponse = callUserDetailedInfo!!.execute()
                currentUser = userResponse.body()
                userCartListResponse = callUserCartList!!.execute()
                currentUser!!.userCarts = (userCartListResponse.body())
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        userUpdateThread.start()
        while (userUpdateThread.isAlive);
        return currentUser
    }

    //SHOP OPERATIONS
    fun getShopContent(): MutableLiveData<Shop?> {
        val call = serverAPI.shop
        call!!.enqueue(object : Callback<Shop?> {
            override fun onResponse(call: Call<Shop?>, response: Response<Shop?>) {
                if (!response.isSuccessful) {
                    shopContent.postValue(null)
                } else {
                    shopContent.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<Shop?>, t: Throwable) {
                shopContent.postValue(null)
            }
        })
        return shopContent
    }

    fun searchShopByKeyword(queryKeyword: String?): MutableLiveData<Shop?> {
        val call = serverAPI.searchShopByKeyword(queryKeyword)
        call!!.enqueue(object : Callback<Shop?> {
            override fun onResponse(call: Call<Shop?>, response: Response<Shop?>) {
                if (!response.isSuccessful) {
                    shopContent.postValue(null)
                } else {
                    shopContent.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<Shop?>, t: Throwable) {
                shopContent.postValue(null)
            }
        })
        return shopContent
    }

    fun searchShopByCategory(categoryTag: String?): MutableLiveData<Shop?> {
        val call = serverAPI.searchShopByCategory(categoryTag)
        call!!.enqueue(object : Callback<Shop?> {
            override fun onResponse(call: Call<Shop?>, response: Response<Shop?>) {
                if (!response.isSuccessful) {
                    shopContent.postValue(null)
                } else {
                    shopContent.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<Shop?>, t: Throwable) {
                shopContent.postValue(null)
            }
        })
        return shopContent
    }

    //CART OPERATIONS
    val usersCart: Cart?
        get() {
            if (cartContent!!.value == null) {
                cartContent!!.value = Cart(totalCartCount, currentUser!!.id)
                totalCartCount++
            }
            return cartContent!!.value
        }

    fun addProductToUsersCart(product: Product?, quantity: Int) {
        val productPerPrice = Math.round(product!!.price)
        val discountedPerPrice =
            Math.round(product!!.price * ((100 - product!!.discountPercentage) / 100))
        val newQuantity: Int
        var productIndex = 0

        //Find product in the cart
        while (productIndex < cartContent!!.value!!.products.size && cartContent!!.value!!.products[productIndex]!!.id != product!!.id) {
            productIndex++
        }

        //If the cart doesn't contain the product
        if (productIndex == cartContent!!.value!!.products.size) {
            val productToAdd = Product(
                product!!.id,
                product!!.title,
                product!!.description,
                product!!.price,
                product!!.discountPercentage,
                product!!.rating,
                product!!.stock,
                product!!.brand,
                product!!.category,
                product!!.thumbnail
            )
            productToAdd.quantity = quantity
            productToAdd.total = (quantity * productPerPrice).toInt()
            productToAdd.discountedPrice = (quantity * discountedPerPrice).toInt()
            cartContent!!.value!!.products.add(productToAdd)
            cartContent!!.value!!.totalProducts = (cartContent!!.value!!.totalProducts + 1)
        } else {
            newQuantity = cartContent!!.value!!.products[productIndex]!!.quantity + quantity
            cartContent!!.value!!.products[productIndex]!!.quantity = newQuantity
            cartContent!!.value!!.products[productIndex]!!.total =
                (newQuantity * productPerPrice).toInt()
            cartContent!!.value!!.products[productIndex]!!.discountedPrice =
                (newQuantity * discountedPerPrice).toInt()
        }
        cartContent!!.value!!.totalQuantity = (cartContent!!.value!!.totalQuantity + quantity)
        cartContent!!.value!!.total = ((cartContent!!.value!!.total + quantity * productPerPrice).toInt())
        cartContent!!.value!!.discountedTotal = ((cartContent!!.value!!.discountedTotal + quantity * discountedPerPrice).toInt())
    }

    fun searchProductInUserCart(productID: Int): Int {
        var productIndex = 0
        while (productIndex < cartContent!!.value!!.products.size && cartContent!!.value!!.products[productIndex]!!.id != productID) {
            productIndex++
        }
        return if (productIndex == cartContent!!.value!!.products.size) -1 else productIndex
    }

    fun removeProductFromUsersCart(product: Product?, quantity: Int) {
        val productPerPrice = product!!.price.roundToInt()
        val discountedPerPrice =
            (product!!.price * ((100 - product!!.discountPercentage) / 100)).roundToInt()
        val newQuantity: Int
        var productIndex = 0

        //Find product in the cart
        while (productIndex < cartContent!!.value!!.products.size && cartContent!!.value!!.products[productIndex]!!.id != product!!.id) {
            productIndex++
        }

        //If the cart contains the product
        if (productIndex < cartContent!!.value!!.products.size) {
            newQuantity = cartContent!!.value!!.products[productIndex]!!.quantity - quantity
            //If the passed amount less than the current product quantity
            if (newQuantity > 0) {
                cartContent!!.value!!.products[productIndex]!!.quantity = newQuantity
                cartContent!!.value!!.products[productIndex]!!.total =
                    (newQuantity * productPerPrice).toInt()
                cartContent!!.value!!.products[productIndex]!!.discountedPrice =
                    (newQuantity * discountedPerPrice).toInt()
                cartContent!!.value!!.totalQuantity = (cartContent!!.value!!.totalQuantity - quantity)
                cartContent!!.value!!.total = ((cartContent!!.value!!.total - quantity * productPerPrice).toInt())
                cartContent!!.value!!.discountedTotal = ((cartContent!!.value!!.discountedTotal - quantity * discountedPerPrice).toInt())
            } else {
                cartContent!!.value!!.totalProducts = (cartContent!!.value!!.totalProducts - 1)
                cartContent!!.value!!.totalQuantity = (cartContent!!.value!!.totalQuantity- cartContent!!.value!!.products[productIndex]!!.quantity)
                cartContent!!.value!!.total = (cartContent!!.value!!.total - cartContent!!.value!!.products[productIndex]!!.total)
                cartContent!!.value!!.discountedTotal = (cartContent!!.value!!.discountedTotal - cartContent!!.value!!.products[productIndex]!!.discountedPrice)
                cartContent!!.value!!.products.removeAt(productIndex)
            }
        }
    }

    fun deleteProductFromUsersCart(product: Product) {
        var productIndex = 0

        //Find product in the cart
        while (productIndex < cartContent!!.value!!.products.size && cartContent!!.value!!.products[productIndex]!!.id != product.id) {
            productIndex++
        }

        //If the cart contains the product
        if (productIndex < cartContent!!.value!!.products.size) {
            cartContent!!.value!!.totalProducts = (cartContent!!.value!!.totalProducts - 1)
            cartContent!!.value!!.totalQuantity = (cartContent!!.value!!.totalQuantity- cartContent!!.value!!.products[productIndex]!!.quantity)
            cartContent!!.value!!.total = (cartContent!!.value!!.total - cartContent!!.value!!.products[productIndex]!!.total)
            cartContent!!.value!!.discountedTotal = (cartContent!!.value!!.discountedTotal - cartContent!!.value!!.products[productIndex]!!.discountedPrice)
            cartContent!!.value!!.products.removeAt(productIndex)
        }
    }

    fun emptyUsersCart() {
        if (cartContent!!.value!!.products.size > 0) {
            cartContent!!.value!!.products.clear()
            cartContent!!.value!!.totalProducts = 0
            cartContent!!.value!!.totalQuantity = 0
            cartContent!!.value!!.total = 0
            cartContent!!.value!!.discountedTotal = 0
        }
    }

    fun deleteUsersCart() {
        if (cartContent!!.value != null){
            cartContent!!.value = null
        }
    }

    fun updateUsersCart(){
        cartContent!!.value?.let {
            currentUser!!.userCarts!!.carts!!.add(it)
        }
    }

    /** CALLBACK INTERFACES */
    interface ILogOperationResponse {
        fun onResponse(user: User?)
        fun onFailure(t: Throwable)
    }

    companion object {
        /**CLASS CONTENT */ //STATIC FIELDS
        private var repo //Repo Instance
                : DummyJsonRepository? = null
        private const val SERVER_URL = "https://dummyjson.com/"

        //STATIC METHODS
        val instance: DummyJsonRepository?
            get() {
                if (repo == null) {
                    repo = DummyJsonRepository()
                }
                return repo
            }
    }
}