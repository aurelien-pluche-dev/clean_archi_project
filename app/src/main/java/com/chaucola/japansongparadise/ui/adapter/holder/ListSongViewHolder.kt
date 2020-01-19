package com.chaucola.japansongparadise.ui.adapter.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chaucola.japansongparadise.R

class ListSongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val title: TextView = itemView.findViewById(R.id.item_list_song_title)
    val img: ImageView = itemView.findViewById(R.id.item_list_song_img)
    val addSong: View = itemView.findViewById(R.id.item_list_song_add)
}