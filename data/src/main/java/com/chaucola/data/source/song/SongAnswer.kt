package com.chaucola.data.source.song

import com.google.gson.annotations.SerializedName

data class SongAnswer(@SerializedName("song") val answer: List<SongSource>)

data class SongSource(
    @SerializedName("title") val title: String,
    @SerializedName("url") val url: String,
    @SerializedName("img") val img: String,
    @SerializedName("from") val from: String
)