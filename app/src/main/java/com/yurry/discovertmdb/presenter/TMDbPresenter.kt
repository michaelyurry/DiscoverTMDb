package com.yurry.discovertmdb.presenter

interface TMDbPresenter {
    fun getGenreList()
    fun getMovieDiscoverList(genre: Int)
    fun getMoreMovieDiscoverList(page: Int, genre: Int)
    fun getMovieDetail(movieId: Int)
    fun getReviewList(movieId: Int)
    fun getMoreReviewList(page: Int, movieId: Int)
    fun getTrailerVideo(movieId: Int)
}