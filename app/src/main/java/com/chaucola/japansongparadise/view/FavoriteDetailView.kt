package com.chaucola.japansongparadise.view

import com.chaucola.japansongparadise.model.FavoriteViewModel

interface FavoriteDetailView : BaseView {

    fun renderFavorite(favorite: FavoriteViewModel)
}