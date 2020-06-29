package com.yurry.discovertmdb

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yurry.discovertmdb.model.Review
import kotlinx.android.synthetic.main.review_item.view.*

class MovieReviewRVAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var reviewList: MutableList<Review> = ArrayList()
    private lateinit var resource: Resources
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.review_item, parent, false)
        resource = parent.context.resources
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val review: Review = getItem(position)
        holder.itemView.review_by.text = (resource.getString(R.string.written_by) + review.author)
        holder.itemView.review_content.text = review.content
    }

    private fun getItem(index: Int): Review {
        return reviewList[index]
    }

    fun setData(reviews: List<Review>) {
        reviewList.clear()
        reviewList.addAll(reviews)
        notifyDataSetChanged()
    }

    fun addData(reviews: List<Review>) {
        reviewList.addAll(reviews)
        notifyDataSetChanged()
    }
}