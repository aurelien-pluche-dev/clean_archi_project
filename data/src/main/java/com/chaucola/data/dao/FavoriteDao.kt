package com.chaucola.data.dao

import androidx.room.*
import com.chaucola.data.entity.FavoriteEntity

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorite: FavoriteEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(favorite: FavoriteEntity)

    @get:Query("SELECT * FROM FavoriteEntity")
    val get: List<FavoriteEntity>?

    @Query("SELECT * FROM FavoriteEntity  WHERE id=:idFavorite")
    fun getFromId(idFavorite: Int): FavoriteEntity

    @Query("UPDATE FavoriteEntity SET list_song = :favoriteListSong WHERE id = :idFavorite")
    fun updateFavorite(idFavorite: Int, favoriteListSong: List<String>)

    @Query("DELETE FROM FavoriteEntity WHERE id = :idFavorite")
    fun delete(idFavorite: Int)

    @Query("DELETE FROM FavoriteEntity")
    fun delete()
}