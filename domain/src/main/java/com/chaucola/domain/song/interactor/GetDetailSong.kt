package com.chaucola.domain.song.interactor

import com.chaucola.domain.song.Song
import com.chaucola.domain.song.SongRepository
import io.reactivex.Single

class GetDetailSong(private val songRepository: SongRepository) {

    fun buildUseCaseSingle(idSong: Int): Single<Song> =
        songRepository.getSong(idSong)
}