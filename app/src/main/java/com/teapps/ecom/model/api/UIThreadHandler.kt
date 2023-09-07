package com.teapps.ecom.model.api

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message

//BILGI: Main thread'e metod ve veri yonlendirmek icin yazdigim arayuz
class UIThreadHandler( //UI thread'de aktif olan aktivite icerigi
    private val UI: Context?
) : Handler(Looper.getMainLooper()) {
    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
    }

    fun dispatch(method: Runnable?) {
        post(method!!)
    }
}