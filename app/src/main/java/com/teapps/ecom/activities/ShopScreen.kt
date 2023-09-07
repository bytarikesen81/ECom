package com.teapps.ecom.activities

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.SearchView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teapps.ecom.R
import com.teapps.ecom.model.api.ShopItemAdapter
import com.teapps.ecom.model.entities.Shop
import com.teapps.ecom.viewmodel.ShopViewModel

class ShopScreen : AppCompatActivity() {
    //View
    private var shopRviewItems: RecyclerView? = null
    private var shopSviewItems: SearchView? = null
    private var shopSpinnerCategories: Spinner? = null

    //ViewModel
    private var shopViewModel: ShopViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_screen)
        shopViewModel = ViewModelProviders.of(this).get(ShopViewModel::class.java)
        initializeUIComponents()
    }

    private fun initializeUIComponents() {
        shopRviewItems = findViewById<View>(R.id.shopRviewItems) as RecyclerView
        shopViewModel!!.allShopContent!!.observe(this, object : Observer<Shop?> {
            override fun onChanged(value: Shop?) {
                if (value == null) {
                    shopViewModel!!.uiThreadHandler.dispatch {
                        Toast.makeText(
                            this@ShopScreen,
                            "Error Loading Products.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    if (value.products!!.size == 0) {
                        shopViewModel!!.uiThreadHandler.dispatch {
                            Toast.makeText(
                                this@ShopScreen,
                                "No items found.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                    updateRecycler(value)
                }
            }
        })
        shopSviewItems = findViewById<View>(R.id.shopSviewItems) as SearchView
        shopSviewItems!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return if (query != "") {
                    shopViewModel!!.getShopByQuery(query)
                    true
                } else false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        shopSviewItems!!.setOnCloseListener {
            shopViewModel!!.allShopContent
            shopSpinnerCategories!!.setSelection(0)
            true
        }
        shopSpinnerCategories = findViewById<View>(R.id.shopSpinnerCategories) as Spinner
        shopSpinnerCategories!!.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    if (position != 0) {
                        shopViewModel!!.getShopByCategory(
                            shopSpinnerCategories!!.getItemAtPosition(
                                position
                            ).toString()
                        )
                    } else {
                        shopViewModel!!.allShopContent
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun updateRecycler(shop: Shop) {
        if (shopViewModel!!.shopItemAdapter == null) {
            val adapter = ShopItemAdapter(application, shop)
            shopViewModel!!.shopItemAdapter = adapter
            shopRviewItems!!.adapter = adapter
            //shopItemAdapter.notifyDataSetChanged();
            val linearLayoutManager = LinearLayoutManager(this@ShopScreen)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            shopRviewItems!!.layoutManager = linearLayoutManager
            shopViewModel!!.shopItemAdapter!!.notifyDataSetChanged()
        } else {
            shopViewModel!!.shopItemAdapter!!.updateShopContent(shop)
        }
    }
}