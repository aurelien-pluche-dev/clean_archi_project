package com.chaucola.japansongparadise.utils

import android.content.Context

private const val JAPAN_SONG_PREFERENCES = "JAPAN_SONG_PREFERENCES"
private const val DB_KEY_PREF = "KEY_DATABASE"

internal fun setDataBase(context: Context, value: String) {
    val sharedPref = context.getSharedPreferences(JAPAN_SONG_PREFERENCES, Context.MODE_PRIVATE)
    val editor = sharedPref.edit()
    editor.putString(DB_KEY_PREF, value)
    editor.apply()
}

internal fun getDataBase(context: Context): String {
    val value: String?
    val sharedPref = context.getSharedPreferences(JAPAN_SONG_PREFERENCES, Context.MODE_PRIVATE)
    value = sharedPref.getString(DB_KEY_PREF, "")
    return value
}