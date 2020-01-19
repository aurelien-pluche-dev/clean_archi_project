package com.chaucola.japansongparadise.model

import com.chaucola.domain.song.Song

class SongViewModel(
    val id: Int,
    val title: String,
    val url: String,
    val img: String,
    val from: String
)

internal fun songMapper(song: Song): SongViewModel {
    return SongViewModel(song.id, song.title, song.url, song.img, song.from)
}

internal fun songsMapper(songs: List<Song>): List<SongViewModel> {
    val listSong = ArrayList<SongViewModel>()
    songs.forEach {
        listSong.add(songMapper(it))
    }
    return listSong
}