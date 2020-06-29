package com.yurry.discovertmdb.rest

import com.yurry.discovertmdb.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDbApi {
    @GET("genre/movie/list")
    fun getGenres(
        @Query("api_key") apiKey: String
    ): Call<GenreResponse>

    @GET("discover/movie")
    fun getMovieDiscoversByGenreId(
        @Query("api_key") apiKey: String,
        @Query("sort_by") sort: String,
        @Query("page") page: Int,
        @Query("with_genres") genre: Int
    ): Call<MovieDiscoverResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<MovieDetailResponse>

    @GET("movie/{movie_id}/reviews")
    fun getMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
        ): Call<ReviewResponse>

    @GET("movie/{movie_id}/videos")
    fun getMovieYoutubeTrailer(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<VideoResponse>
}