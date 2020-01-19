package com.chaucola.domain.favorite.interactor

import com.chaucola.domain.favorite.FavoriteRepository
import io.reactivex.Single

class RemoveFavorite(val favoriteRepository: FavoriteRepository) {

    fun buildUseCaseSingle(idFavorite: Int): Single<Boolean> =
        favoriteRepository.removeFavorite(idFavorite)
}