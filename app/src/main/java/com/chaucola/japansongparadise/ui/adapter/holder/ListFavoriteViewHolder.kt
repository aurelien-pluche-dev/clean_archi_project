package com.chaucola.japansongparadise.ui.adapter.holder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chaucola.japansongparadise.R

class ListFavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val title: TextView = itemView.findViewById(R.id.item_list_favorite_title)
    val removeItem: View = itemView.findViewById(R.id.item_list_favorite_remove)
}