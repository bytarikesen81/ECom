package com.teapps.ecom.model.api

import android.graphics.Bitmap
import java.io.IOException

interface ImageAPI {
    @Throws(IOException::class)
    fun downloadImage(imgAddr: String?): Bitmap
}