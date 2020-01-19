package com.chaucola.japansongparadise.view

import com.chaucola.japansongparadise.model.SongViewModel

interface SongDetailView : BaseView {

    fun renderSong(song: SongViewModel)
}