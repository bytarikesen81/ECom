package com.teapps.ecom.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.gson.Gson
import com.teapps.ecom.R
import com.teapps.ecom.model.api.ImageGenerator
import com.teapps.ecom.model.api.ProductItemPSlideAdapter
import com.teapps.ecom.model.entities.Product
import com.teapps.ecom.model.repository.DummyJsonRepository
import com.teapps.ecom.viewmodel.ProductViewModel
import java.io.IOException

class ProductScreen : AppCompatActivity() {
    //View
    private var productTxtBrand: TextView? = null
    private var productTxtTitle: TextView? = null
    private var productTxtCategory: TextView? = null
    private var productTxtRating: TextView? = null
    private var productTxtDp: TextView? = null
    private var productTxtDesc: TextView? = null
    private var productTxtNormalPrice: TextView? = null
    private var productTxtDiscPrice: TextView? = null
    private var productImgRating: ImageView? = null
    private var productVpagerPictures: ViewPager2? = null
    private var productSbarPictures: SeekBar? = null
    private var productBtnAddcart: Button? = null

    //ViewModel
    private var productViewModel: ProductViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_screen)
        val jsonParser = Gson()
        productViewModel = ViewModelProviders.of(this)[ProductViewModel::class.java]
        productViewModel!!.init(
            jsonParser.fromJson(
                intent.getStringExtra("selectedProduct"),
                Product::class.java
            )
        )
        initializeUIComponents()
    }

    private fun initializeUIComponents() {
        val discountedPrice: Double
        val currentProduct = productViewModel!!.currentProduct
        productTxtBrand = findViewById<View>(R.id.productTxtBrand) as TextView
        productTxtBrand!!.text = "" + currentProduct!!.brand
        productTxtTitle = findViewById<View>(R.id.productTxtTitle) as TextView
        productTxtTitle!!.text = "" + currentProduct!!.title
        productTxtCategory = findViewById<View>(R.id.productTxtCategory) as TextView
        productTxtCategory!!.text = "" + currentProduct!!.category
        productTxtRating = findViewById<View>(R.id.productTxtRating) as TextView
        productTxtRating!!.text = "" + currentProduct!!.rating
        productImgRating = findViewById<View>(R.id.productImgRating) as ImageView
        productImgRating!!.setImageResource(R.drawable.ratingicon)
        productTxtDp = findViewById<View>(R.id.productTxtDp) as TextView
        productTxtDp!!.text = "Is %" + currentProduct!!.discountPercentage + " off"
        productTxtDesc = findViewById<View>(R.id.productTxtDesc) as TextView
        productTxtDesc!!.text = "" + currentProduct!!.description
        productVpagerPictures = findViewById<View>(R.id.productVpagerPictures) as ViewPager2
        setupViewPager()
        productTxtNormalPrice = findViewById<View>(R.id.productTxtNormalPrice) as TextView
        productTxtNormalPrice!!.text = "" + Math.round(currentProduct.price) + "$"
        productTxtNormalPrice!!.paintFlags =
            productTxtNormalPrice!!.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        productTxtDiscPrice = findViewById<View>(R.id.productTxtDiscPrice) as TextView
        discountedPrice = currentProduct.price * ((100 - currentProduct.discountPercentage) / 100)
        productTxtDiscPrice!!.text = "" + String.format("%d", Math.round(discountedPrice)) + "$"
        productSbarPictures = findViewById<View>(R.id.productSbarPictures) as SeekBar
        productSbarPictures!!.max = productViewModel!!.productImages!!.size - 1
        productSbarPictures!!.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            private var mProgressAtStartTracking = 0
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar) {
                mProgressAtStartTracking = seekBar.progress
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                productVpagerPictures!!.currentItem = seekBar.progress
            }
        })
        productBtnAddcart = findViewById<View>(R.id.productBtnAddcart) as Button
        productBtnAddcart!!.setOnClickListener {
            productViewModel!!.addItemToUserCart()
            /*
            val productIndexInCart = productViewModel!!.getCurrentProductInCart(currentProduct.id)
            Toast.makeText(
                this@ProductScreen, """
     Product added to user cart:${
                    DummyJsonRepository.Companion.instance!!.usersCart!!.products
                        .get(productIndexInCart)!!.title
                }
     Quantity:${
                    DummyJsonRepository.Companion.instance!!.usersCart!!.products
                        .get(productIndexInCart)!!.quantity
                }
     """.trimIndent(), Toast.LENGTH_LONG
            ).show()
            Toast.makeText(
                this@ProductScreen, """
     Cart Total Type of Product:${
                    DummyJsonRepository.Companion.instance!!.usersCart!!.totalProducts
                }
     Cart Total Quantity:${
                    DummyJsonRepository.Companion.instance!!.usersCart!!.totalQuantity
                }
     Cart Total Price:${DummyJsonRepository.Companion.instance!!.usersCart!!.total}
     Cart Total Discounted Price:${
                    DummyJsonRepository.Companion.instance!!.usersCart!!.discountedTotal
                }
     """.trimIndent(), Toast.LENGTH_LONG
            ).show()*/
            startActivity(Intent(this@ProductScreen, HomeScreen::class.java))
        }
    }

    private fun setupViewPager() {
        //Get product images
        setupImages()

        //Initialize Image Adapter
        val productItemPSlideAdapter =
            ProductItemPSlideAdapter(this@ProductScreen, productViewModel!!.productImages)
        productViewModel!!.productItemPSlideAdapter = productItemPSlideAdapter

        //Apply the Adapter
        productVpagerPictures!!.adapter = productItemPSlideAdapter

        //Set Pager Direction Flow
        productVpagerPictures!!.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        //Organize Swiping Event Logic
        productVpagerPictures!!.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                productSbarPictures!!.progress = position
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })
    }

    private fun setupImages() {
        val networkImageDownloadThread = Thread {
            val imageUrls = productViewModel!!.currentProduct!!.images
            val imageGenerator = ImageGenerator()
            var currentImage: Bitmap?
            for (imageUrl in imageUrls!!) {
                try {
                    currentImage = imageGenerator.downloadImage(imageUrl)
                    productViewModel!!.productImages!!.add(currentImage)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        networkImageDownloadThread.start()
        while (networkImageDownloadThread.isAlive);
    }
}