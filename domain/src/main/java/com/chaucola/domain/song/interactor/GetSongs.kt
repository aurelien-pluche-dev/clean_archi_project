package com.chaucola.domain.song.interactor

import com.chaucola.domain.song.Song
import com.chaucola.domain.song.SongRepository
import io.reactivex.Single

class GetSongs(private val songRepository: SongRepository) {

    fun buildUseCaseSingle(): Single<List<Song>> =
        songRepository.getSongs()
}