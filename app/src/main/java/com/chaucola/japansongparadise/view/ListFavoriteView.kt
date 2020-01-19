package com.chaucola.japansongparadise.view

import com.chaucola.japansongparadise.model.FavoriteViewModel

interface ListFavoriteView : BaseView {

    fun renderFavorites(favorites: List<FavoriteViewModel>)
}