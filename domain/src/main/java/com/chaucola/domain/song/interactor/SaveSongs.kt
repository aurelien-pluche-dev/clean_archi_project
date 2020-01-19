package com.chaucola.domain.song.interactor

import com.chaucola.domain.song.Song
import com.chaucola.domain.song.SongRepository
import io.reactivex.Single

class SaveSongs(private val songRepository: SongRepository) {

    fun buildUseCaseSingle(songs: List<Song>): Single<Boolean> =
        songRepository.saveSongs(songs)
}