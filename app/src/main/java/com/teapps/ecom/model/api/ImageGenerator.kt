package com.teapps.ecom.model.api

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.IOException
import java.net.URL

class ImageGenerator : ImageAPI {
    @Throws(IOException::class)
    override fun downloadImage(imgAddr: String?): Bitmap {
        val url = URL(imgAddr)
        return BitmapFactory.decodeStream(url.openConnection().getInputStream())
    }
}