package com.chaucola.japansongparadise.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chaucola.japansongparadise.R
import com.chaucola.japansongparadise.model.FavoriteViewModel
import com.chaucola.japansongparadise.ui.adapter.holder.ListFavoriteViewHolder

class ListFavoriteAdapter(private val needShowDelete: Boolean) :
    RecyclerView.Adapter<ListFavoriteViewHolder>() {

    interface OnItemClickListener {
        fun onDelete(favoriteId: Int)
        fun onDetails(favoriteId: Int, favoriteTitle: String)
    }

    private var onItemClickListener: OnItemClickListener? = null
    private var dataList: List<FavoriteViewModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListFavoriteViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_favorite, parent, false)
        return ListFavoriteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListFavoriteViewHolder, position: Int) {
        val favoriteModel = dataList[position]

        if (needShowDelete) {
            holder.removeItem.visibility = View.VISIBLE
        } else {
            holder.removeItem.visibility = View.GONE
        }

        holder.title.text = favoriteModel.title

        holder.removeItem.setOnClickListener {
            onItemClickListener?.onDelete(favoriteModel.id)
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.onDetails(favoriteModel.id, favoriteModel.title)
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun setDataList(list: List<FavoriteViewModel>) {
        this.dataList = list
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

}