package com.chaucola.japansongparadise.presenter

import com.chaucola.domain.favorite.Favorite
import com.chaucola.domain.favorite.interactor.GetFavorite
import com.chaucola.japansongparadise.model.favoriteMapper
import com.chaucola.japansongparadise.view.FavoriteDetailView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FavoriteDetailPresenter(private val getFavorite: GetFavorite) : BasePresenter<Favorite>() {

    override fun showInView(content: Favorite) {
        (view as FavoriteDetailView).renderFavorite(favoriteMapper(content))
    }

    fun initialize(view: FavoriteDetailView, idFavorite: Int) {
        this.view = view
        showViewLoading()
        getFavorite.buildUseCaseSingle(idFavorite)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(defaultEntityObserver())
    }

}