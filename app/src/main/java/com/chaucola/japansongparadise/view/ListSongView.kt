package com.chaucola.japansongparadise.view

import com.chaucola.japansongparadise.model.SongViewModel

interface ListSongView : BaseView {

    fun renderSongs(songs: List<SongViewModel>)

}