package com.chaucola.data.source.dbversion

import com.google.gson.annotations.SerializedName

data class DbVersionAnswer(@SerializedName("version") val answer: String)