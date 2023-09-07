package com.teapps.ecom.model.api

import android.app.Application
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.teapps.ecom.R
import com.teapps.ecom.activities.ProductScreen
import com.teapps.ecom.activities.fragments.ShopItemViewHolder
import com.teapps.ecom.model.entities.Shop
import java.io.IOException

class ShopItemAdapter(private val application: Application, private val shopContent: Shop) :
    RecyclerView.Adapter<ShopItemViewHolder>() {
    private val uiThreadHandler: UIThreadHandler

    init {
        uiThreadHandler = UIThreadHandler(application.applicationContext)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val shopItemView = LayoutInflater.from(application.applicationContext)
            .inflate(R.layout.fragment_shop_item, parent, false)
        return ShopItemViewHolder(shopItemView)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val imageGenerator = ImageGenerator()
        holder.itemTxtTitle!!.text = "" + shopContent.products?.get(holder.adapterPosition)!!.title
        holder.itemTxtDesc!!.text = "" + shopContent.products?.get(holder.adapterPosition)!!.description
        holder.itemTxtPrice!!.text = "" + shopContent.products?.get(holder.adapterPosition)!!.price + " $"
        holder.itemTxtRating!!.text = "" + shopContent.products?.get(holder.adapterPosition)!!.rating
        holder.itemImgRating!!.setImageResource(R.drawable.ratingicon)
        val networkImgThread = Thread {
            val itemImage: Bitmap?
            try {
                itemImage =
                    imageGenerator.downloadImage(shopContent.products!![holder.adapterPosition]!!.thumbnail)
                uiThreadHandler.dispatch { holder.itemImgThumbnail!!.setImageBitmap(itemImage) }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        networkImgThread.start()
        holder.itemContainer!!.setOnClickListener {
            val selectedProduct = shopContent.products!![holder.adapterPosition]
            val jsonPacker = Gson()
            val intent = Intent(application.applicationContext, ProductScreen::class.java)
            intent.putExtra("selectedProduct", jsonPacker.toJson(selectedProduct))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            application.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return shopContent.products!!.size
    }

    fun updateShopContent(content: Shop) {
        shopContent.products!!.clear()
        shopContent.products!!.addAll(content.products!!)
        notifyDataSetChanged()
    }
}