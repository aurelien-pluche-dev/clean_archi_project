package com.chaucola.data.mapper

import com.chaucola.data.entity.SongEntity
import com.chaucola.data.source.song.SongAnswer
import com.chaucola.data.source.song.SongSource
import com.chaucola.domain.song.Song

internal fun songMapper(songSource: SongSource): Song {
    return Song(0, songSource.title, songSource.url, songSource.img, songSource.from)
}

internal fun songAnswerMapper(songAnswer: SongAnswer): List<Song> {
    val songList = ArrayList<Song>()
    val answer = songAnswer.answer
    answer.forEach {
        songList.add(songMapper(it))
    }
    return songList
}

internal fun songsEntityMapper(songs: List<Song>): List<SongEntity> {
    val songEntityList = ArrayList<SongEntity>()
    songs.forEach {
        songEntityList.add(SongEntity(it.title, it.url, it.img, it.from))
    }
    return songEntityList
}

internal fun songMapper(song: SongEntity): Song {
    return Song(song.id, song.title, song.url, song.img, song.from)
}

internal fun songsMapper(songs: List<SongEntity>?): List<Song> {
    val songList = ArrayList<Song>()
    songs?.forEach {
        songList.add(songMapper(it))
    }
    return songList
}

