package com.chaucola.data.source.dbversion

import com.chaucola.data.source.song.ISong
import com.chaucola.data.source.song.SongAnswer
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

internal const val BASE_URL = "https://www.chaucola.ovh/"

class DbVersionService {

    private var service: IDbVersion? = null

    fun getService(): IDbVersion {
        service = buildService()
        return service!!
    }

    private fun buildService(): IDbVersion {
        val builder = OkHttpClient.Builder()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(builder.build())
            .build()

        return retrofit.create(IDbVersion::class.java)
    }

    fun getSongs(): Single<DbVersionAnswer> {
        return getService().getVersion().subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
    }
}