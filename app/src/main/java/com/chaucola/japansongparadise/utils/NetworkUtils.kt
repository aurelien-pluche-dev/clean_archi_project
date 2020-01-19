package com.chaucola.japansongparadise.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

internal fun isOnline(context: Context?): Boolean {
    return if (context == null) {
        false
    } else {
        val cm =
            context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo: NetworkInfo? = cm.activeNetworkInfo
        netInfo != null && netInfo.isConnected
    }
}