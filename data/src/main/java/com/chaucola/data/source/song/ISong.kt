package com.chaucola.data.source.song

import io.reactivex.Single
import retrofit2.http.GET

interface ISong {
    @GET("chaucola/music/song_japan.json")
    fun getSongs(): Single<SongAnswer>
}