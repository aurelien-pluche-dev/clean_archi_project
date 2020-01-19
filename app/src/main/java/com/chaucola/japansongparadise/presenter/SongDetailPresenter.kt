package com.chaucola.japansongparadise.presenter

import com.chaucola.domain.song.Song
import com.chaucola.domain.song.interactor.GetDetailSong
import com.chaucola.japansongparadise.model.songMapper
import com.chaucola.japansongparadise.view.SongDetailView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SongDetailPresenter(val getDetailSong: GetDetailSong) : BasePresenter<Song>() {

    override fun showInView(content: Song) {
        (view as SongDetailView).renderSong(songMapper(content))
    }

    fun initialize(view: SongDetailView, idSong: Int) {
        this.view = view
        showViewLoading()
        getDetailSong.buildUseCaseSingle(idSong)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(defaultEntityObserver())
    }

}