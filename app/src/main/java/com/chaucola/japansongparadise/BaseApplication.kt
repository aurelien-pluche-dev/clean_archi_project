package com.chaucola.japansongparadise

import androidx.multidex.MultiDexApplication
import com.chaucola.data.repository.DbVersionDataRepository
import com.chaucola.data.repository.FavoriteDataRepository
import com.chaucola.data.repository.SongDataRepository
import com.chaucola.domain.dbversion.DbVersionRepository
import com.chaucola.domain.favorite.FavoriteRepository
import com.chaucola.domain.song.SongRepository

class BaseApplication : MultiDexApplication() {

    val dbVersionRepository: DbVersionRepository by lazy {
        DbVersionDataRepository()
    }

    val favoriteRepository: FavoriteRepository by lazy {
        FavoriteDataRepository(applicationContext)
    }

    val songRepository: SongRepository by lazy {
        SongDataRepository(applicationContext)
    }
}