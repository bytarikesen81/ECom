package com.teapps.ecom.viewmodel

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import com.teapps.ecom.model.api.ProductItemPSlideAdapter
import com.teapps.ecom.model.entities.Product
import com.teapps.ecom.model.repository.DummyJsonRepository

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    /**Repositories */ /*Repo 1*/
    private val repo //Repo Object
            : DummyJsonRepository?
    //Model data from Repo 1
    //Getters and Setters
    /**Activity Backend Objects */
    var productImages: ArrayList<Bitmap?>? = null
    var currentProduct: Product? = null
    var productItemPSlideAdapter: ProductItemPSlideAdapter? = null

    /**Constructor */
    init {
        repo = DummyJsonRepository.Companion.instance
    }

    fun init(parsedProduct: Product) {
        productImages = ArrayList()
        currentProduct = parsedProduct
    }

    /**Business Logic Methods */
    fun addItemToUserCart() {
        repo!!.addProductToUsersCart(currentProduct, 1)
    }

    fun getCurrentProductInCart(productID: Int): Int {
        return repo!!.searchProductInUserCart(productID)
    }
}