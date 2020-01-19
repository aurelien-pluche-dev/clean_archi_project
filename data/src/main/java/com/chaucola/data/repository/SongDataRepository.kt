package com.chaucola.data.repository

import android.content.Context
import com.chaucola.data.dao.SongDao
import com.chaucola.data.mapper.songAnswerMapper
import com.chaucola.data.mapper.songMapper
import com.chaucola.data.mapper.songsEntityMapper
import com.chaucola.data.mapper.songsMapper
import com.chaucola.data.source.AppDatabase
import com.chaucola.data.source.song.SongService
import com.chaucola.domain.song.Song
import com.chaucola.domain.song.SongRepository
import io.reactivex.Single

class SongDataRepository(private val context: Context) : SongRepository {

    private val songService = SongService()

    override fun getSongsFromDatabse(): Single<List<Song>> {
        return songService.getSongs().map {
            songAnswerMapper(it)
        }
    }

    override fun saveSongs(songs: List<Song>): Single<Boolean> {
        return Single.create { e ->
            val songDao: SongDao = AppDatabase.getInstance(context).songDao()
            songDao.delete()
            songDao.insertAll(songsEntityMapper(songs))
            if (!e.isDisposed) {
                e.onSuccess(true)
            }
        }
    }

    override fun getSongs(): Single<List<Song>> {
        return Single.create { e ->
            val songDao: SongDao = AppDatabase.getInstance(context).songDao()
            if (!e.isDisposed) {
                e.onSuccess(songsMapper(songDao.get))
            }
        }
    }

    override fun getSongsFromFavorite(idSongs: List<Int>): Single<List<Song>> {
        return Single.create { e ->
            val songDao: SongDao = AppDatabase.getInstance(context).songDao()
            if (!e.isDisposed) {
                e.onSuccess(songsMapper(songDao.getAllFromId(idSongs)))
            }
        }
    }

    override fun getSong(idSong: Int): Single<Song> {
        return Single.create { e ->
            val songDao: SongDao = AppDatabase.getInstance(context).songDao()
            if (!e.isDisposed) {
                e.onSuccess(songMapper(songDao.getFromId(idSong)))
            }
        }
    }

}