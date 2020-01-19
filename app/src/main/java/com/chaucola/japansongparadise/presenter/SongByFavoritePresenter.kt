package com.chaucola.japansongparadise.presenter

import com.chaucola.domain.song.Song
import com.chaucola.domain.song.interactor.GetSongsFromFavorite
import com.chaucola.japansongparadise.model.songsMapper
import com.chaucola.japansongparadise.view.ListSongView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SongByFavoritePresenter(private val getSongsFromIds: GetSongsFromFavorite) :
    BasePresenter<List<Song>>() {

    override fun showInView(content: List<Song>) {
        (view as ListSongView).renderSongs(songsMapper(content))
    }

    fun initialize(view: ListSongView, idSongs: List<Int>) {
        this.view = view
        showViewLoading()
        getSongsFromIds.buildUseCaseSingle(idSongs)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(defaultEntityObserver())
    }

}