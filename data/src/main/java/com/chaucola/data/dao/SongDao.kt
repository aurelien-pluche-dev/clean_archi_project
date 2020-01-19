package com.chaucola.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chaucola.data.entity.SongEntity

@Dao
interface SongDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(song: SongEntity): Long

    @Insert
    fun insertAll(songs: List<SongEntity>)

    @get:Query("SELECT * FROM SongEntity")
    val get: List<SongEntity>?

    @Query("SELECT * FROM SongEntity  WHERE id=:idSong")
    fun getFromId(idSong: Int): SongEntity

    @Query("SELECT * FROM SongEntity  WHERE id IN (:idSongs)")
    fun getAllFromId(idSongs: List<Int>): List<SongEntity>

    @Query("DELETE FROM SongEntity")
    fun delete()
}