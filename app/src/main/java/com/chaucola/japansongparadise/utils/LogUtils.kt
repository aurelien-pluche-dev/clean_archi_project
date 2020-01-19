package com.chaucola.japansongparadise.utils

import android.util.Log
import com.chaucola.japansongparadise.BuildConfig

internal fun log(tag: String, e: Throwable) {
    if (BuildConfig.DEBUG) {
        Log.e(tag, e.message, e)
    }
}