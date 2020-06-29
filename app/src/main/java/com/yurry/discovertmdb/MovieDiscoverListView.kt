package com.yurry.discovertmdb

import com.yurry.discovertmdb.model.MovieDiscover

interface MovieDiscoverListView {
    fun setData(movieDiscoverList: List<MovieDiscover>)
    fun addData(movieDiscoverList: List<MovieDiscover>)
    fun showLoading()
    fun hideLoading()
    fun showErrorToast(msg: String)
    fun showEmptyDataToast()
}