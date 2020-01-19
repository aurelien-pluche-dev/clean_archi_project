package com.chaucola.domain.favorite.interactor

import com.chaucola.domain.favorite.FavoriteRepository
import io.reactivex.Single

class AddFavorite(val favoriteRepository: FavoriteRepository) {

    fun buildUseCaseSingle(title: String): Single<Boolean> =
        favoriteRepository.addFavorite(title)
}