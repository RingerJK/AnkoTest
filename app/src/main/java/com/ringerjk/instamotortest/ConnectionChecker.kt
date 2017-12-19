package com.ringerjk.instamotortest

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by yurykanetski on 12/9/17.
 */

@Singleton
class ConnectionChecker @Inject constructor() {

    @Inject lateinit var connectivityManager: ConnectivityManager

    @SuppressLint("MissingPermission")
    fun getNetworkStatus(): Int {
        val networkInfo = connectivityManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnectedOrConnecting) {
            when (networkInfo.type) {
                ConnectivityManager.TYPE_WIFI -> return WIFI
                ConnectivityManager.TYPE_MOBILE -> return MOBILE
                else -> return OFFLINE
            }
        }

        return OFFLINE
    }

    @SuppressLint("MissingPermission")
    fun isOnline(): Boolean {
        val activeNetwork = connectivityManager.activeNetworkInfo

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    companion object NetworkStatus {
        val OFFLINE = 1
        val MOBILE = 2
        val WIFI = 3
    }
}