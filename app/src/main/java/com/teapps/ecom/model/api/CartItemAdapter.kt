package com.teapps.ecom.model.api

import android.graphics.Bitmap
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.teapps.ecom.R
import com.teapps.ecom.model.entities.Cart
import com.teapps.ecom.model.entities.Shop
import com.teapps.ecom.model.repository.DummyJsonRepository
import java.io.IOException

class CartItemAdapter(private val application: Fragment, cartContent: Cart) :
    RecyclerView.Adapter<CartItemAdapter.ViewHolder>() {
    private val uiThreadHandler: UIThreadHandler
    private val cartContent: Cart

    init {
        uiThreadHandler = UIThreadHandler(application.context)
        this.cartContent = cartContent
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = LayoutInflater.from(application.context)
            .inflate(R.layout.subfragment_mycart_item, parent, false)
        return ViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageGenerator = ImageGenerator()
        val txtPrice = application.requireView().findViewById<TextView>(R.id.mycartTxtNormalPrice)
        val txtDPrice = application.requireView().findViewById<TextView>(R.id.mycartTxtDPrice)
        holder.cartItemTxtBrand.text = "" + cartContent.products[holder.adapterPosition]!!.brand
        holder.cartItemTxtTitle.text = "" + cartContent.products[holder.adapterPosition]!!.title
        holder.cartItemTxtDesc.text = "" + cartContent.products[holder.adapterPosition]!!.description
        holder.cartItemTxtCount.text = "" + cartContent.products[holder.adapterPosition]!!.quantity
        holder.cartItemTxtPrice.paintFlags =
            holder.cartItemTxtPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        holder.cartItemTxtPrice.text = "" + cartContent.products[holder.adapterPosition]!!.total + "$"
        holder.cartItemTxtDiscountedPrice.text =
            "" + cartContent.products[holder.adapterPosition]!!.discountedPrice + "$"
        val networkImgThread = Thread {
            val itemImage: Bitmap?
            try {
                itemImage =
                    imageGenerator.downloadImage(cartContent.products[holder.adapterPosition]!!.thumbnail)
                uiThreadHandler.dispatch { holder.cartItemImgThumbnail.setImageBitmap(itemImage) }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        networkImgThread.start()
        holder.btnDeleteItem.setImageResource(R.drawable.baseline_delete_outline_24)
        holder.btnDeleteItem.setOnClickListener {
            cartContent.products[holder.adapterPosition]?.let { product ->
                DummyJsonRepository.Companion.instance!!.deleteProductFromUsersCart(
                    product
                )
            }
            notifyDataSetChanged()
            txtPrice.text = "" + cartContent.total
            txtDPrice.text = "" + cartContent.discountedTotal
        }
        holder.btnCountUp.setOnClickListener {
            DummyJsonRepository.Companion.instance!!.addProductToUsersCart(cartContent.products[holder.adapterPosition], 1)
            notifyDataSetChanged()
            txtPrice.text = "" + cartContent.total + "$"
            txtDPrice.text = "" + cartContent.discountedTotal + "$"
        }
        if (cartContent.products[holder.adapterPosition]!!.quantity == 1){
            holder.btnCountDown.isEnabled = false
        }
        else holder.btnCountDown.isEnabled = true
        holder.btnCountDown.setOnClickListener {
            DummyJsonRepository.Companion.instance!!.removeProductFromUsersCart(cartContent.products[holder.adapterPosition], 1)
            notifyDataSetChanged()
            txtPrice.text = "" + cartContent.total + "$"
            txtDPrice.text = "" + cartContent.discountedTotal + "$"
        }
    }

    fun clearCartContent() {
        cartContent.products!!.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return cartContent.products.size
    }

    /**ViewHolder Class */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemCartRoot: CardView
        var cartItemImgThumbnail: ImageView
        var cartItemTxtBrand: TextView
        var cartItemTxtTitle: TextView
        var cartItemTxtDesc: TextView
        var cartItemTxtCount: TextView
        var cartItemTxtPrice: TextView
        var cartItemTxtDiscountedPrice: TextView
        var btnCountUp: Button
        var btnCountDown: Button
        var btnDeleteItem: ImageButton

        init {
            itemCartRoot = itemView.findViewById<View>(R.id.itemCartRoot) as CardView
            cartItemImgThumbnail =
                itemView.findViewById<View>(R.id.cartItemImgThumbnail) as ImageView
            cartItemTxtBrand = itemView.findViewById<View>(R.id.cartItemTxtBrand) as TextView
            cartItemTxtTitle = itemView.findViewById<View>(R.id.cartItemTxtTitle) as TextView
            cartItemTxtDesc = itemView.findViewById<View>(R.id.cartItemTxtDesc) as TextView
            cartItemTxtCount = itemView.findViewById<View>(R.id.cartItemTxtCount) as TextView
            cartItemTxtPrice = itemView.findViewById<View>(R.id.cartItemTxtPrice) as TextView
            cartItemTxtDiscountedPrice =
                itemView.findViewById<View>(R.id.cartItemTxtDiscountedPrice) as TextView
            btnCountUp = itemView.findViewById<View>(R.id.btnCountUp) as Button
            btnCountDown = itemView.findViewById<View>(R.id.btnCountDown) as Button
            btnDeleteItem = itemView.findViewById<View>(R.id.btnDeleteItem) as ImageButton
        }
    }
}