package com.chaucola.domain.favorite.interactor

import com.chaucola.domain.favorite.Favorite
import com.chaucola.domain.favorite.FavoriteRepository
import io.reactivex.Single

class GetFavorite(private val favoriteRepository: FavoriteRepository) {

    fun buildUseCaseSingle(idFavorite: Int): Single<Favorite> =
        favoriteRepository.getFavorite(idFavorite)
}