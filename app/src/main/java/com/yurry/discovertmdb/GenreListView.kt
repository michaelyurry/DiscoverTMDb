package com.yurry.discovertmdb

import com.yurry.discovertmdb.model.Genre

interface GenreListView {
    fun hideLoading()
    fun setData(genreList: List<Genre>)
    fun showErrorToast(msg: String)
    fun showEmptyDataToast()
}