package com.chaucola.data.source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.chaucola.data.dao.FavoriteDao
import com.chaucola.data.dao.SongDao
import com.chaucola.data.entity.FavoriteEntity
import com.chaucola.data.entity.SongEntity

@Database(entities = [SongEntity::class, FavoriteEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun songDao(): SongDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "japan_song_paradise.db"
                    )
                        .build()
                }
            }
            return INSTANCE!!
        }
    }

}