package com.chaucola.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.chaucola.data.utils.ConverterUtils

@Entity
class FavoriteEntity(
    @ColumnInfo(name = "title") val title: String,
    @field:TypeConverters(ConverterUtils::class) @ColumnInfo(name = "list_song") var listSongId: List<String>
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}