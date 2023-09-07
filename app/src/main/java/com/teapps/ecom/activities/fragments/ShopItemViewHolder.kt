package com.teapps.ecom.activities.fragments

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.teapps.ecom.R

class ShopItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    //Frontend Objects
    var itemContainer: CardView? = null
        private set
    var itemTxtTitle: TextView? = null
    var itemTxtDesc: TextView? = null
    var itemTxtRating: TextView? = null
    var itemTxtPrice: TextView? = null
    var itemImgThumbnail: ImageView? = null
    var itemImgRating: ImageView? = null

    init {
        initializeUIComponents(itemView)
    }

    private fun initializeUIComponents(itemView: View) {
        itemContainer = itemView.findViewById<View>(R.id.itemRoot) as CardView
        itemTxtTitle = itemView.findViewById<View>(R.id.itemTxtTitle) as TextView
        itemTxtDesc = itemView.findViewById<View>(R.id.itemTxtDesc) as TextView
        itemTxtRating = itemView.findViewById<View>(R.id.itemTxtRating) as TextView
        itemTxtPrice = itemView.findViewById<View>(R.id.itemTxtPrice) as TextView
        itemImgThumbnail = itemView.findViewById<View>(R.id.itemImgThumbnail) as ImageView
        itemImgRating = itemView.findViewById<View>(R.id.itemImgRating) as ImageView
    }
}