package com.chaucola.data.source.dbversion

import io.reactivex.Single
import retrofit2.http.GET

interface IDbVersion {
    @GET("chaucola/music/version.json")
    fun getVersion(): Single<DbVersionAnswer>
}