package com.chaucola.domain.song

import io.reactivex.Single

interface SongRepository {

    fun getSongsFromDatabse(): Single<List<Song>>

    fun saveSongs(songs: List<Song>): Single<Boolean>

    fun getSongs(): Single<List<Song>>

    fun getSongsFromFavorite(idSongs: List<Int>): Single<List<Song>>

    fun getSong(idSong: Int): Single<Song>

}