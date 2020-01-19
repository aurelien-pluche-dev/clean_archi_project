package com.chaucola.domain.favorite.interactor

import com.chaucola.domain.favorite.FavoriteRepository
import com.chaucola.domain.favorite.Favorite
import io.reactivex.Single

class GetFavorites(val favoriteRepository: FavoriteRepository) {

    fun buildUseCaseSingle(): Single<List<Favorite>> =
        favoriteRepository.favoriteDetails()
}