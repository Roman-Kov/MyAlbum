package com.rojer_ko.myalbum.data.network

import android.content.Context
import android.net.ConnectivityManager
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

class NetworkManager(private val context: Context) {

    companion object {
        private const val CONNECTION_PORT = 443
        private const val CONNECTION_TIMEOUT = 5000
    }

    fun isNetworkAvailable(): Boolean {
        val cm: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (cm.activeNetwork == null) return false

        return try {
            val sock = Socket()
            sock.connect(InetSocketAddress(BASE_URL_HOST, CONNECTION_PORT), CONNECTION_TIMEOUT)
            sock.close()
            true
        } catch (e: IOException) {
            false
        }
    }
}