package com.yurry.discovertmdb

import com.yurry.discovertmdb.model.Review

interface MovieReviewListView {
    fun hideLoading()
    fun showLoading()
    fun setData(list: List<Review>)
    fun addData(list: List<Review>)
    fun showErrorToast(msg: String)
    fun showEmptyDataToast()
}