package com.chaucola.domain.favorite.interactor

import com.chaucola.domain.favorite.FavoriteRepository
import io.reactivex.Single

class UpdateFavoris(private val favoriteRepository: FavoriteRepository) {

    fun buildUseCaseSingle(idFavorite: Int, idSong: Int): Single<Boolean> =
        favoriteRepository.updateFavorite(idFavorite, idSong)
}