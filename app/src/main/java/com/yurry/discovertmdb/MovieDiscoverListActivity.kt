package com.yurry.discovertmdb

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yurry.discovertmdb.model.MovieDiscover
import com.yurry.discovertmdb.presenter.TMDbPresenter
import com.yurry.discovertmdb.presenter.TMDbPresenterImpl
import kotlinx.android.synthetic.main.activity_movie_discover_list.*

class MovieDiscoverListActivity : AppCompatActivity(), MovieDiscoverListView {
    private val presenter: TMDbPresenter = TMDbPresenterImpl(this)
    private lateinit var adapter: MovieDiscoverRVAdapter
    private lateinit var scrollListener: EndlessScrollListener
    private var genreId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_discover_list)
        setRecyclerView()
        genreId = intent.getIntExtra(Constant.GENRE_KEY, 0)
        if(genreId != 0){
            presenter.getMovieDiscoverList(genreId)
        }
    }

    private fun setRecyclerView() {
        val gridLayoutManager =
            GridLayoutManager(this, 2)
        adapter = MovieDiscoverRVAdapter()
        movie_discover_recycler_view.layoutManager = gridLayoutManager
        movie_discover_recycler_view.adapter = adapter

        adapter.setItemClickListener(object : MovieDiscoverRVAdapter.ItemClickListener {
            override fun onItemClick(view: View, movieDiscover: MovieDiscover) {
                val intent = Intent(applicationContext, MovieDetailActivity::class.java)
                intent.putExtra(Constant.MOVIE_KEY, movieDiscover.id)
                startActivity(intent)
            }
        })

        scrollListener = EndlessScrollListener(gridLayoutManager)
        scrollListener.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore(
                currentPage: Int,
                totalItemCount: Int,
                recyclerView: RecyclerView
            ) {
                if(genreId != 0){
                    presenter.getMoreMovieDiscoverList(currentPage, genreId)
                    showLoading()
                }
            }
        })
        movie_discover_recycler_view.addOnScrollListener(scrollListener)
    }

    override fun setData(movieDiscoverList: List<MovieDiscover>) {
        adapter.setData(movieDiscoverList)
    }

    override fun addData(movieDiscoverList: List<MovieDiscover>) {
        adapter.addData(movieDiscoverList)
    }

    override fun showLoading() {
        movie_detail_loading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        movie_detail_loading.visibility = View.GONE
    }

    override fun showErrorToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun showEmptyDataToast() {
        Toast.makeText(this, getString(R.string.empty_data), Toast.LENGTH_SHORT).show()
    }
}