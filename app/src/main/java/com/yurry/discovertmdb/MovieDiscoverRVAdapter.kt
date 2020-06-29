package com.yurry.discovertmdb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yurry.discovertmdb.model.MovieDiscover
import kotlinx.android.synthetic.main.movie_discover_item.view.*

class MovieDiscoverRVAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var movieDiscoverList: MutableList<MovieDiscover> = ArrayList()
    private lateinit var mClickListener: ItemClickListener

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_discover_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieDiscoverList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val picasso = Picasso.get()
        val movieDiscover = getItem(position)

        picasso.load(Constant.IMAGE_500_URL + movieDiscover.posterPath)
            .placeholder(R.drawable.placeholder)
            .into(holder.itemView.movie_discover_image)
        holder.itemView.setOnClickListener {v -> mClickListener.onItemClick(v, movieDiscover)}
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.mClickListener = itemClickListener
    }

    private fun getItem(index: Int): MovieDiscover {
        return movieDiscoverList[index]
    }

    interface ItemClickListener{
        fun onItemClick(view: View, movieDiscover: MovieDiscover)
    }

    fun setData(movieDiscovers: List<MovieDiscover>) {
        movieDiscoverList.clear()
        movieDiscoverList.addAll(movieDiscovers)
        notifyDataSetChanged()
    }

    fun addData(movieDiscovers: List<MovieDiscover>) {
        movieDiscoverList.addAll(movieDiscovers)
        notifyDataSetChanged()

    }
}