package com.yurry.discovertmdb.presenter

import com.yurry.discovertmdb.*
import com.yurry.discovertmdb.model.*
import com.yurry.discovertmdb.rest.APIErrorUtils
import com.yurry.discovertmdb.rest.TMDbRestClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TMDbPresenterImpl : TMDbPresenter{
    private lateinit var genreListView: GenreListView
    private lateinit var movieDiscoverListView: MovieDiscoverListView
    private lateinit var detailView: MovieDetailView
    private lateinit var reviewListView: MovieReviewListView

    constructor(view: GenreListView){
        genreListView = view
    }

    constructor(view: MovieDiscoverListView){
        movieDiscoverListView = view
    }

    constructor(view: MovieDetailView){
        detailView = view
    }

    constructor(view: MovieReviewListView){
        reviewListView = view
    }

    override fun getGenreList() {
        TMDbRestClient().getService()
            .getGenres(Constant.API_KEY)
            .enqueue(object: Callback<GenreResponse> {
                override fun onFailure(call: Call<GenreResponse>, t: Throwable) {
                    genreListView.showErrorToast(t.message.toString())
                    genreListView.hideLoading()
                }

                override fun onResponse(
                    call: Call<GenreResponse>,
                    response: Response<GenreResponse>
                ) {
                    if(response.isSuccessful) {
                        if (response.body() != null) {
                            if(response.body()!!.genres.isEmpty()){
                                genreListView.showEmptyDataToast()
                            } else{
                                genreListView.setData(response.body()!!.genres)
                            }
                            genreListView.hideLoading()
                        }
                    } else {
                        response.code()
                        genreListView.showErrorToast(APIErrorUtils().parseError(response)?.message.toString())
                        genreListView.hideLoading()

                    }
                }
            })
    }

    override fun getMovieDiscoverList(genre: Int) {
        TMDbRestClient().getService()
            .getMovieDiscoversByGenreId(Constant.API_KEY, Constant.TMDB_POPULARITY, 1, genre)
            .enqueue(object: Callback<MovieDiscoverResponse>{
                override fun onFailure(call: Call<MovieDiscoverResponse>, t: Throwable) {
                    movieDiscoverListView.showErrorToast(t.message.toString())
                    movieDiscoverListView.hideLoading()
                }

                override fun onResponse(
                    call: Call<MovieDiscoverResponse>,
                    response: Response<MovieDiscoverResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            if (response.body()!!.totalResults != 0){
                                movieDiscoverListView.setData(response.body()!!.results)
                            } else{
                                movieDiscoverListView.showEmptyDataToast()
                            }
                            movieDiscoverListView.hideLoading()
                        }
                    } else {
                        genreListView.showErrorToast(APIErrorUtils().parseError(response)?.message.toString())
                        movieDiscoverListView.hideLoading()
                    }
                }
            })
    }

    override fun getMoreMovieDiscoverList(page: Int, genre: Int) {
        TMDbRestClient().getService()
            .getMovieDiscoversByGenreId(Constant.API_KEY, Constant.TMDB_POPULARITY, page, genre)
            .enqueue(object: Callback<MovieDiscoverResponse>{
                override fun onFailure(call: Call<MovieDiscoverResponse>, t: Throwable) {
                    movieDiscoverListView.showErrorToast(t.message.toString())
                    movieDiscoverListView.hideLoading()
                }

                override fun onResponse(
                    call: Call<MovieDiscoverResponse>,
                    response: Response<MovieDiscoverResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            movieDiscoverListView.addData(response.body()!!.results)
                            movieDiscoverListView.hideLoading()
                        }
                    } else {
                        genreListView.showErrorToast(APIErrorUtils().parseError(response)?.message.toString())
                        movieDiscoverListView.hideLoading()
                    }
                }

            })
    }

    override fun getMovieDetail(movieId: Int) {
        TMDbRestClient().getService()
            .getMovieDetail(movieId, Constant.API_KEY)
            .enqueue(object: Callback<MovieDetailResponse>{
                override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                    detailView.showErrorToast(t.message.toString())
                }

                override fun onResponse(
                    call: Call<MovieDetailResponse>,
                    response: Response<MovieDetailResponse>
                ) {
                    if(response.isSuccessful) {
                        if (response.body() != null) {
                            detailView.setMovieDetail(response.body()!!)
                        }
                    } else {
                        detailView.showErrorToast(APIErrorUtils().parseError(response)?.message.toString())
                    }
                }

            })
        }

    override fun getReviewList(movieId: Int) {
        TMDbRestClient().getService()
            .getMovieReviews(movieId, Constant.API_KEY, 1)
            .enqueue(object: Callback<ReviewResponse>{
                override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                    reviewListView.showErrorToast(t.message.toString())
                    reviewListView.hideLoading()
                }

                override fun onResponse(
                    call: Call<ReviewResponse>,
                    response: Response<ReviewResponse>
                ) {
                    if(response.isSuccessful) {
                        if (response.body() != null){
                            if (response.body()!!.totalResults != 0){
                                reviewListView.setData(response.body()!!.results)
                            } else {
                                reviewListView.showEmptyDataToast()
                            }
                            reviewListView.hideLoading()
                        }
                    } else {
                        print(APIErrorUtils().parseError(response)?.message)
                        reviewListView.hideLoading()
                    }
                }

            })
    }

    override fun getMoreReviewList(page: Int, movieId: Int) {
        TMDbRestClient().getService()
            .getMovieReviews(movieId, Constant.API_KEY, page)
            .enqueue(object: Callback<ReviewResponse>{
                override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                    reviewListView.showErrorToast(t.message.toString())
                    reviewListView.hideLoading()
                }

                override fun onResponse(
                    call: Call<ReviewResponse>,
                    response: Response<ReviewResponse>
                ) {
                    if(response.isSuccessful) {
                        if (response.body() != null){
                            reviewListView.addData(response.body()!!.results)
                            reviewListView.hideLoading()
                        }

                    } else {
                        print(APIErrorUtils().parseError(response)?.message)
                        reviewListView.hideLoading()
                    }
                }

            })
    }

    override fun getTrailerVideo(movieId: Int) {
        TMDbRestClient().getService()
            .getMovieYoutubeTrailer(movieId, Constant.API_KEY)
            .enqueue(object: Callback<VideoResponse>{
                override fun onFailure(call: Call<VideoResponse>, t: Throwable) {
                    reviewListView.showErrorToast(t.message.toString())
                }

                override fun onResponse(
                    call: Call<VideoResponse>,
                    response: Response<VideoResponse>
                ) {
                    if(response.isSuccessful) {
                        if (response.body() != null) {
                            val videos = response.body()!!.results
                            for (video in videos) {
                                if (video.type == Constant.TRAILER_TYPE_KEY && video.site == Constant.YOUTUBE_TYPE_KEY) {
                                    detailView.setYoutubePlayer(video.key)
                                    break
                                }
                            }
                        }

                    } else {
                        reviewListView.showErrorToast(APIErrorUtils().parseError(response)?.message.toString())
                    }

                }

            })
    }

}