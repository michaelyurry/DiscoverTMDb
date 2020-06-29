package com.yurry.discovertmdb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yurry.discovertmdb.model.Genre
import kotlinx.android.synthetic.main.genre_item.view.*

class GenreRVAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var genreList: MutableList<Genre> = ArrayList()
    private lateinit var mClickListener: ItemClickListener

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.genre_item, parent, false)
        return ItemViewHolder(view)

    }

    override fun getItemCount(): Int {
        return genreList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val genre: Genre = getItem(position)
        holder.itemView.genre_text.text = genre.name
        holder.itemView.setOnClickListener {v -> mClickListener.onItemClick(v, genre)}
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.mClickListener = itemClickListener
    }

    interface ItemClickListener{
        fun onItemClick(view: View, genre: Genre)
    }

    private fun getItem(index: Int): Genre {
        return genreList[index]
    }

    fun setGenreList(genres: List<Genre>) {
        genreList.clear()
        genreList.addAll(genres)
        notifyDataSetChanged()
    }

}