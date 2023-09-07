package com.teapps.ecom.model.api

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
import com.teapps.ecom.model.entities.CartBase

class OrderHistoryAdapter (private val application: Fragment, orderHistory: CartBase) :
    RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder>() {
    private val orderHistory: CartBase

    init{
        this.orderHistory = orderHistory
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = LayoutInflater.from(application.context).inflate(R.layout.subfragment_orderhistory_item, parent, false)
        return ViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: OrderHistoryAdapter.ViewHolder, position: Int) {
        var index = itemCount - (position + 1)

        holder.orderHistItemTxtHeader.text = "Cart " + (index+1)

        var productList = ""
        for(cartProduct in orderHistory!!.carts!![index].products) {
            productList += ("(x" + cartProduct!!.quantity + ") ")
            productList += (cartProduct.title + "\n")
        }
        holder.orderHistItemTxtProducts.text = productList

        holder.orderHistItemTxtProductCount.text = "Total Products:" + orderHistory!!.carts!![index].totalProducts

        holder.orderHistItemTxtQuantityCount.text = "Total Item Count:" + orderHistory!!.carts!![index].totalQuantity

        holder.orderHistItemTxtPrice.paintFlags = holder.orderHistItemTxtPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        holder.orderHistItemTxtPrice.text = "" + orderHistory!!.carts!![index].total + "$"

        holder.orderHistItemTxtDiscountedPrice.text = "" + orderHistory!!.carts!![index].discountedTotal + "$"
    }

    override fun getItemCount(): Int {
        return orderHistory.carts!!.size
    }

    /**ViewHolder Class */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var orderHistItemTxtHeader : TextView
        var orderHistItemTxtProducts : TextView
        var orderHistItemTxtProductCount : TextView
        var orderHistItemTxtQuantityCount : TextView
        var orderHistItemTxtPrice : TextView
        var orderHistItemTxtDiscountedPrice : TextView

        init {
            orderHistItemTxtHeader = itemView.findViewById<View>(R.id.orderHistItemTxtHeader) as TextView
            orderHistItemTxtProducts = itemView.findViewById<View>(R.id.orderHistItemTxtProducts) as TextView
            orderHistItemTxtProductCount = itemView.findViewById<View>(R.id.orderHistItemTxtProductCount) as TextView
            orderHistItemTxtQuantityCount = itemView.findViewById<View>(R.id.orderHistItemTxtQuantityCount) as TextView
            orderHistItemTxtPrice = itemView.findViewById<View>(R.id.orderHistItemTxtPrice) as TextView
            orderHistItemTxtDiscountedPrice =
                itemView.findViewById<View>(R.id.orderHistItemTxtDiscountedPrice) as TextView
        }
    }
}