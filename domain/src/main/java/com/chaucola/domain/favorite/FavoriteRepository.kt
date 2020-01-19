package com.chaucola.domain.favorite

import io.reactivex.Single

interface FavoriteRepository {

    fun favoriteDetails(): Single<List<Favorite>>

    fun getFavorite(idFavorite: Int): Single<Favorite>

    fun addFavorite(title: String): Single<Boolean>

    fun updateFavorite(idFavorite: Int, idSong: Int): Single<Boolean>

    fun removeFavorite(idFavorite: Int): Single<Boolean>
}