package com.teapps.ecom.activities.ui.mycart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teapps.ecom.activities.HomeScreen
import com.teapps.ecom.databinding.FragmentMycartBinding
import com.teapps.ecom.model.api.CartItemAdapter
import com.teapps.ecom.model.api.UIThreadHandler
import com.teapps.ecom.model.entities.Cart

class MyCartFragment : Fragment() {
    //View
    private var mycartTxtNormalPrice: TextView? = null
    private var mycartTxtDPrice: TextView? = null
    private var mycartTxtHeaderPrice: TextView? = null
    var binding: FragmentMycartBinding? = null
        private set
    private var mycartRviewItems: RecyclerView? = null
    private var mycartComplete: Button? = null

    //ViewModel
    private var myCartViewModel: MyCartViewModel? = null
    private var uiThreadHandler: UIThreadHandler? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myCartViewModel = ViewModelProvider(this).get(MyCartViewModel::class.java)
        uiThreadHandler = UIThreadHandler(this.context)
        binding = FragmentMycartBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        initializeUIComponents()
        return root
    }

    private fun initializeUIComponents() {
        mycartTxtNormalPrice = binding!!.mycartTxtNormalPrice
        mycartTxtDPrice = binding!!.mycartTxtDPrice
        mycartTxtHeaderPrice = binding!!.mycartTxtHeaderPrice
        mycartRviewItems = binding!!.mycartRviewItems
        myCartViewModel!!.allCartContent!!.observe(viewLifecycleOwner, object : Observer<Cart?> {
            override fun onChanged(value: Cart?) {
                if(value != null){
                    if (value!!.products.size == 0) {
                        /*
                        uiThreadHandler!!.dispatch {
                            Toast.makeText(
                                this@MyCartFragment.context,
                                "No items found.",
                                Toast.LENGTH_LONG
                            ).show()
                        }*/
                    }
                    mycartTxtNormalPrice!!.text = "" + value.total + "$"
                    mycartTxtDPrice!!.text = "" + value.discountedTotal + "$"
                    updateRecycler(value)
                }
            }
        })

        mycartComplete = binding!!.mycartComplete
        mycartComplete!!.setOnClickListener {
            if(myCartViewModel!!.cartItemAdapter!!.itemCount > 0){
                myCartViewModel!!.makeCheckout()
                myCartViewModel!!.cartItemAdapter!!.notifyDataSetChanged()
                startActivity(Intent(this.context, HomeScreen::class.java))
                Toast.makeText(this@MyCartFragment.context, "Checkout Completed. Check your order from history menu.", Toast.LENGTH_LONG).show()
            }
            else
                Toast.makeText(this@MyCartFragment.context, "Your cart is empty!", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateRecycler(cart: Cart) {
        if (mycartRviewItems!!.adapter == null) {
            if (myCartViewModel!!.cartItemAdapter == null) {
                val cartItemAdapter = CartItemAdapter(this@MyCartFragment, cart)
                myCartViewModel!!.cartItemAdapter = cartItemAdapter
            }
            mycartRviewItems!!.adapter = myCartViewModel!!.cartItemAdapter
            val linearLayoutManager = LinearLayoutManager(context)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            mycartRviewItems!!.layoutManager = linearLayoutManager
        }
        myCartViewModel!!.cartItemAdapter!!.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}