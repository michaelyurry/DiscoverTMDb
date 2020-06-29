package com.yurry.discovertmdb

import androidx.recyclerview.widget.RecyclerView

interface OnLoadMoreListener {
    fun onLoadMore(currentPage: Int, totalItemCount: Int, recyclerView: RecyclerView)
}