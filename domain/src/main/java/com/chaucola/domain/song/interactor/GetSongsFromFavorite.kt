package com.chaucola.domain.song.interactor

import com.chaucola.domain.song.Song
import com.chaucola.domain.song.SongRepository
import io.reactivex.Single

class GetSongsFromFavorite(private val songRepository: SongRepository) {

    fun buildUseCaseSingle(idSongs: List<Int>): Single<List<Song>> =
        songRepository.getSongsFromFavorite(idSongs)
}