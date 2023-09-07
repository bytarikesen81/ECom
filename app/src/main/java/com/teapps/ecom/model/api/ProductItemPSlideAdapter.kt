package com.teapps.ecom.model.api

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.teapps.ecom.R

class ProductItemPSlideAdapter(
    private val applicationContext: Context,
    var productImages: ArrayList<Bitmap?>?
) : RecyclerView.Adapter<ProductItemPSlideAdapter.ViewHolder>() {
    private val pLayoutInflater: LayoutInflater? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(applicationContext)
            .inflate(R.layout.fragment_product_imageview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageViewMain.setImageBitmap(productImages!![position])
    }

    override fun getItemCount(): Int {
        return productImages!!.size
    }

    /**ViewHolder Class */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageViewMain: ImageView

        init {
            imageViewMain = itemView.findViewById<View>(R.id.imageViewMain) as ImageView
        }
    }
}