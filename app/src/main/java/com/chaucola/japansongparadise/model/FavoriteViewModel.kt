package com.chaucola.japansongparadise.model

import com.chaucola.domain.favorite.Favorite
import java.util.ArrayList

class FavoriteViewModel(val id: Int, val title: String, val listSong: List<Int>)

internal fun favoriteMapper(favorite: Favorite): FavoriteViewModel {
    return FavoriteViewModel(favorite.id, favorite.title, favorite.idSongs)
}

internal fun favoritesMapper(favorites: List<Favorite>): List<FavoriteViewModel> {
    val listFavorite = ArrayList<FavoriteViewModel>()
    favorites.forEach {
        listFavorite.add(favoriteMapper(it))
    }
    return listFavorite
}