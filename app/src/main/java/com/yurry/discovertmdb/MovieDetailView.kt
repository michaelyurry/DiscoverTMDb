package com.yurry.discovertmdb

import com.yurry.discovertmdb.model.MovieDetailResponse

interface MovieDetailView {
    fun setYoutubePlayer(videoId: String)
    fun setMovieDetail(movieDetailResponse: MovieDetailResponse)
    fun hideLoading()
    fun showErrorToast(msg: String)
}