package com.teapps.ecom.activities.ui.orderhistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teapps.ecom.model.api.CartItemAdapter
import com.teapps.ecom.model.api.OrderHistoryAdapter
import com.teapps.ecom.model.entities.Cart
import com.teapps.ecom.model.entities.CartBase
import com.teapps.ecom.model.repository.DummyJsonRepository

class OrderHistoryViewModel : ViewModel() {
    /**Repositories */ /*Repo 1*/
    private val repo //Repo Object
            : DummyJsonRepository?

    //Model data from Repo 1
    private var orderHistory: CartBase?

    /**Activity Backend Objects */
    var orderHistoryAdapter: OrderHistoryAdapter? = null

    /**Constructor**/
    init {
        repo = DummyJsonRepository.Companion.instance
        orderHistory = CartBase()
    }

    /**Business Logic Methods */
    val getOrderHistory: CartBase?
        get() {
            orderHistory = repo!!.currentUser!!.userCarts
            return orderHistory
        }
}