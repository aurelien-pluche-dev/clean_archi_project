package com.chaucola.data.source.song

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

internal const val BASE_URL = "https://www.chaucola.ovh/"

class SongService {

    private var service: ISong? = null

    fun getService(): ISong {
        service = buildService()
        return service!!
    }

    private fun buildService(): ISong {
        val builder = OkHttpClient.Builder()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(builder.build())
            .build()

        return retrofit.create(ISong::class.java)
    }

    fun getSongs(): Single<SongAnswer> {
        return getService().getSongs().subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
    }
}