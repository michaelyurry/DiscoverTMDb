package com.yurry.discovertmdb

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yurry.discovertmdb.model.Review
import com.yurry.discovertmdb.presenter.TMDbPresenter
import com.yurry.discovertmdb.presenter.TMDbPresenterImpl
import kotlinx.android.synthetic.main.activity_movie_review_list.*

class MovieReviewListActivity: AppCompatActivity(), MovieReviewListView{
    private val presenter: TMDbPresenter = TMDbPresenterImpl(this)
    private lateinit var adapter: MovieReviewRVAdapter
    private lateinit var scrollListener: EndlessScrollListener
    private var movieId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_review_list)
        setRecyclerView()
        movieId = intent.getIntExtra(Constant.MOVIE_KEY, 0)
        if(movieId != 0){
            presenter.getReviewList(movieId)
        }
    }

    private fun setRecyclerView() {
        val linearLayoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        adapter = MovieReviewRVAdapter()
        movie_review_recycler_view.layoutManager = linearLayoutManager
        movie_review_recycler_view.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(
            movie_review_recycler_view.context,
            LinearLayoutManager.VERTICAL
        )
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.divider))
        movie_review_recycler_view.addItemDecoration(dividerItemDecoration)

        scrollListener = EndlessScrollListener(linearLayoutManager)
        scrollListener.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore(
                currentPage: Int,
                totalItemCount: Int,
                recyclerView: RecyclerView
            ) {
                if(movieId != 0){
                    presenter.getMoreReviewList(currentPage, movieId)
                    showLoading()
                }
            }
        })
        movie_review_recycler_view.addOnScrollListener(scrollListener)
    }

    override fun hideLoading() {
        movie_detail_loading.visibility = View.GONE
    }

    override fun showLoading() {
        movie_detail_loading.visibility = View.VISIBLE
    }

    override fun setData(list: List<Review>) {
        adapter.setData(list)
    }

    override fun addData(list: List<Review>) {
        adapter.addData(list)
    }

    override fun showErrorToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun showEmptyDataToast() {
        Toast.makeText(this, getString(R.string.empty_data), Toast.LENGTH_SHORT).show()
    }
}