package com.chaucola.japansongparadise.presenter

import com.chaucola.domain.song.Song
import com.chaucola.domain.song.interactor.GetSongs
import com.chaucola.japansongparadise.model.songsMapper
import com.chaucola.japansongparadise.view.ListSongView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SongPresenter(val getSongs: GetSongs) : BasePresenter<List<Song>>() {

    override fun showInView(content: List<Song>) {
        (view as ListSongView).renderSongs(songsMapper(content))
    }

    fun initialize(view: ListSongView) {
        this.view = view
        showViewLoading()
        getSongs.buildUseCaseSingle()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(defaultEntityObserver())
    }
}