package com.teapps.ecom.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.teapps.ecom.model.api.ShopItemAdapter
import com.teapps.ecom.model.api.UIThreadHandler
import com.teapps.ecom.model.entities.Shop
import com.teapps.ecom.model.repository.DummyJsonRepository

class ShopViewModel(application: Application) : AndroidViewModel(application) {
    /**Repositories */ /*Repo 1*/
    private val repo //Repo Object
            : DummyJsonRepository?

    //Model data from Repo 1
    private var shopContent: MutableLiveData<Shop?>?
    //Getters and Setters
    /**Activity Backend Objects */
    var shopItemAdapter: ShopItemAdapter? = null
    val uiThreadHandler: UIThreadHandler

    /**Constructor */
    init {
        repo = DummyJsonRepository.Companion.instance
        uiThreadHandler = UIThreadHandler(application.applicationContext)
        shopContent = MutableLiveData()
    }

    /**Business Logic Methods */
    val allShopContent: MutableLiveData<Shop?>?
        get() {
            shopContent = repo!!.getShopContent()
            return shopContent
        }

    fun getShopByQuery(queryKeyword: String?): MutableLiveData<Shop?>? {
        shopContent = repo!!.searchShopByKeyword(queryKeyword)
        return shopContent
    }

    fun getShopByCategory(categoryTag: String?): MutableLiveData<Shop?>? {
        shopContent = repo!!.searchShopByCategory(categoryTag)
        return shopContent
    }
}