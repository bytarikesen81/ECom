package com.teapps.ecom.activities.ui.orderhistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teapps.ecom.databinding.FragmentOrderhistoryBinding
import com.teapps.ecom.model.api.CartItemAdapter
import com.teapps.ecom.model.api.OrderHistoryAdapter
import com.teapps.ecom.model.api.UIThreadHandler
import com.teapps.ecom.model.entities.Cart
import com.teapps.ecom.model.entities.CartBase

class OrderHistoryFragment : Fragment() {
    //View
    private var binding: FragmentOrderhistoryBinding? = null
    private var orderhistRviewItems: RecyclerView? = null

    //ViewModel
    private var orderHistoryViewModel:OrderHistoryViewModel? = null
    private var uiThreadHandler: UIThreadHandler? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        orderHistoryViewModel = ViewModelProvider(this).get(OrderHistoryViewModel::class.java)
        uiThreadHandler = UIThreadHandler(this.context)
        binding = FragmentOrderhistoryBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        initializeUIComponents()
        return root
    }


    private fun initializeUIComponents(){
        orderhistRviewItems = binding!!.orderhistRviewItems
        updateRecycler()
    }

    private fun updateRecycler() {
        if (orderhistRviewItems!!.adapter == null) {
            val orderAdapter = orderHistoryViewModel!!.getOrderHistory?.let {
                OrderHistoryAdapter(this@OrderHistoryFragment,
                    it
                )
            }
            orderHistoryViewModel!!.orderHistoryAdapter = orderAdapter
            orderhistRviewItems!!.adapter = orderHistoryViewModel!!.orderHistoryAdapter
            val linearLayoutManager = LinearLayoutManager(context)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            orderhistRviewItems!!.layoutManager = linearLayoutManager
        }
        orderHistoryViewModel!!.orderHistoryAdapter!!.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}