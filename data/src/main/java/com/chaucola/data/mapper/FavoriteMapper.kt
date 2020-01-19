package com.chaucola.data.mapper

import com.chaucola.data.entity.FavoriteEntity
import com.chaucola.domain.favorite.Favorite

internal fun favoriteMapper(favoriteEntity: FavoriteEntity): Favorite {
    return Favorite(
        favoriteEntity.id,
        favoriteEntity.title,
        favoriteIdSongMapper(favoriteEntity.listSongId)
    )
}

internal fun favoritesMapper(favoritesEntity: List<FavoriteEntity>?): List<Favorite> {
    val favoriteList = ArrayList<Favorite>()
    favoritesEntity?.forEach {
        favoriteList.add(favoriteMapper(it))
    }
    return favoriteList
}

internal fun favoriteIdSongMapper(listSongId: List<String>?): List<Int> {
    val favoriteListSong = ArrayList<Int>()
    listSongId?.forEach {
        favoriteListSong.add(it.toInt())
    }
    return favoriteListSong
}