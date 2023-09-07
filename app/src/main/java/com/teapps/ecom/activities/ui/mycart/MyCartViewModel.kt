package com.teapps.ecom.activities.ui.mycart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teapps.ecom.model.api.CartItemAdapter
import com.teapps.ecom.model.entities.Cart
import com.teapps.ecom.model.repository.DummyJsonRepository

class MyCartViewModel : ViewModel() {
    /**Repositories */ /*Repo 1*/
    private val repo //Repo Object
            : DummyJsonRepository?

    //Model data from Repo 1
    private var cartContent: MutableLiveData<Cart?>?


    /**Activity Backend Objects */
    var cartItemAdapter: CartItemAdapter? = null

    /**Constructor */
    init {
        repo = DummyJsonRepository.Companion.instance
        cartContent = MutableLiveData()
    }

    /**Business Logic Methods */
    val allCartContent: MutableLiveData<Cart?>?
        get() {
            cartContent = repo!!.cartContent
            return cartContent
        }

    fun makeCheckout(){
        repo!!.updateUsersCart()
        repo!!.deleteUsersCart()
        repo.usersCart
    }
}