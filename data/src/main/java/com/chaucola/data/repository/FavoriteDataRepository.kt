package com.chaucola.data.repository

import android.content.Context
import com.chaucola.data.entity.FavoriteEntity
import com.chaucola.data.mapper.favoriteMapper
import com.chaucola.data.mapper.favoritesMapper
import com.chaucola.data.source.AppDatabase
import com.chaucola.domain.favorite.FavoriteRepository
import com.chaucola.domain.favorite.Favorite
import io.reactivex.Single

class FavoriteDataRepository(private val context: Context) :
    FavoriteRepository {

    override fun favoriteDetails(): Single<List<Favorite>> {
        return Single.create { e ->
            val favoriteDao = AppDatabase.getInstance(context).favoriteDao()
            if (!e.isDisposed) {
                e.onSuccess(favoritesMapper(favoriteDao.get))
            }
        }
    }

    override fun updateFavorite(idFavorite: Int, idSong: Int): Single<Boolean> {
        return Single.create { e ->
            val favoriteDao = AppDatabase.getInstance(context).favoriteDao()
            val favorite = favoriteDao.getFromId(idFavorite)
            favorite.listSongId = handleListSong(favorite.listSongId, idSong.toString())
            favoriteDao.update(favorite)
            if (!e.isDisposed) {
                e.onSuccess(true)
            }
        }
    }

    private fun handleListSong(listSongId: List<String>?, idSong: String): List<String> {
        val favoriteListSong = ArrayList<String>()
        listSongId?.forEach {
            favoriteListSong.add(it)
        }
        if (!favoriteListSong.contains(idSong)) {
            favoriteListSong.add(idSong)
        }
        return favoriteListSong
    }

    override fun getFavorite(idFavorite: Int): Single<Favorite> {
        return Single.create { e ->
            val favoriteDao = AppDatabase.getInstance(context).favoriteDao()
            if (!e.isDisposed) {
                e.onSuccess(favoriteMapper(favoriteDao.getFromId(idFavorite)))
            }
        }
    }

    override fun addFavorite(title: String): Single<Boolean> {
        return Single.create { e ->
            val favoriteDao = AppDatabase.getInstance(context).favoriteDao()
            val success = favoriteDao.insert(FavoriteEntity(title, emptyList()))
            if (!e.isDisposed) {
                e.onSuccess(success > 0)
            }
        }
    }

    override fun removeFavorite(idFavorite: Int): Single<Boolean> {
        return Single.create { e ->
            val favoriteDao = AppDatabase.getInstance(context).favoriteDao()
            favoriteDao.delete(idFavorite)
            if (!e.isDisposed) {
                e.onSuccess(true)
            }
        }
    }
}