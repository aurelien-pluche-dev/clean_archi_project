package com.chaucola.japansongparadise.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chaucola.japansongparadise.R
import com.chaucola.japansongparadise.model.SongViewModel
import com.chaucola.japansongparadise.ui.adapter.holder.ListSongViewHolder
import com.squareup.picasso.Picasso

class ListSongAdapter(private val needShowAdd: Boolean) :
    RecyclerView.Adapter<ListSongViewHolder>() {

    interface OnItemClickListener {
        fun onAdd(song: SongViewModel)
        fun onPlay(song: SongViewModel)
    }

    private var onItemClickListener: OnItemClickListener? = null
    private var dataList: List<SongViewModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSongViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_song, parent, false)
        return ListSongViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListSongViewHolder, position: Int) {
        val songModel = dataList[position]

        if (needShowAdd){
            holder.addSong.visibility = View.VISIBLE
        } else{
            holder.addSong.visibility = View.GONE
        }

        holder.title.text = songModel.title
        Picasso.get().load(songModel.img).fit().into(holder.img)

        holder.addSong.setOnClickListener {
            onItemClickListener?.onAdd(songModel)
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.onPlay(songModel)
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun setDataList(list: List<SongViewModel>) {
        this.dataList = list
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }
}