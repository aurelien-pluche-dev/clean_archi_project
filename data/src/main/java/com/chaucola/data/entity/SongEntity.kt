package com.chaucola.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class SongEntity(@ColumnInfo(name = "title") val title: String,
                 @ColumnInfo(name = "url") val url: String,
                 @ColumnInfo(name = "img") val img: String,
                 @ColumnInfo(name = "from") val from: String) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}