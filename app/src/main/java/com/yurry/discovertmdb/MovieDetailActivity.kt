package com.yurry.discovertmdb

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.squareup.picasso.Picasso
import com.yurry.discovertmdb.model.MovieDetailResponse
import com.yurry.discovertmdb.presenter.TMDbPresenter
import com.yurry.discovertmdb.presenter.TMDbPresenterImpl
import kotlinx.android.synthetic.main.activity_movie_detail.*


class MovieDetailActivity: AppCompatActivity(), MovieDetailView {
    private val presenter: TMDbPresenter = TMDbPresenterImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val genreId = intent.getIntExtra(Constant.MOVIE_KEY, 0)
        if(genreId != 0) {
            presenter.getMovieDetail(genreId)
            presenter.getTrailerVideo(genreId)
        }
    }

    override fun setYoutubePlayer(videoId: String) {
        lifecycle.addObserver(third_party_player_view)
        third_party_player_view.getPlayerUiController().showFullscreenButton(true)
        third_party_player_view.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(videoId, 0f)
            }
        })

        third_party_player_view.getPlayerUiController().setFullScreenButtonClickListener(View.OnClickListener {
            if (third_party_player_view.isFullScreen()) {
                third_party_player_view.exitFullScreen()
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
                // Show ActionBar
                if (supportActionBar != null) {
                    supportActionBar!!.show()
                }
            } else {
                third_party_player_view.enterFullScreen()
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
                // Hide ActionBar
                if (supportActionBar != null) {
                    supportActionBar!!.hide()
                }
            }
        })
    }

    override fun setMovieDetail(movieDetailResponse: MovieDetailResponse) {
        hideLoading()
        val picasso = Picasso.get()
        picasso.load(Constant.IMAGE_500_URL + movieDetailResponse.posterPath)
            .placeholder(R.drawable.placeholder)
            .into(movie_detail_image)

        movie_title.text = movieDetailResponse.originalTitle
        release_date.text = movieDetailResponse.releaseDate
        duration.text = (movieDetailResponse.runtime.toString()+ getString(R.string.duration_min))
        rating.text = movieDetailResponse.voteAverage.toString()

        if (movieDetailResponse.tagline.isNotEmpty()){
            tagline.text = movieDetailResponse.tagline
        } else {
            tagline.visibility = View.GONE
        }
        overview.text = movieDetailResponse.overview

        review_button.setOnClickListener {
            val intent = Intent(applicationContext, MovieReviewListActivity::class.java)
            intent.putExtra(Constant.MOVIE_KEY, movieDetailResponse.id)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        lifecycle.removeObserver(third_party_player_view)
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (third_party_player_view.isFullScreen()) {
            third_party_player_view.exitFullScreen()
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
            // Show ActionBar
            if (supportActionBar != null) {
                supportActionBar!!.show()
            }
        } else {
            super.onBackPressed()
        }
    }

    override fun hideLoading(){
        review_button.visibility = View.VISIBLE
        movie_detail_loading.visibility = View.GONE
    }

    override fun showErrorToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}