package com.chaucola.japansongparadise.presenter

import com.chaucola.domain.favorite.Favorite
import com.chaucola.domain.favorite.interactor.GetFavorites
import com.chaucola.japansongparadise.model.favoritesMapper
import com.chaucola.japansongparadise.view.ListFavoriteView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FavoritePresenter(val getFavorites: GetFavorites) : BasePresenter<List<Favorite>>() {

    override fun showInView(content: List<Favorite>) {
        (view as ListFavoriteView).renderFavorites(favoritesMapper(content))
    }

    fun initialize(view: ListFavoriteView) {
        this.view = view
        showViewLoading()
        getFavorites.buildUseCaseSingle()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(defaultEntityObserver())
    }

}