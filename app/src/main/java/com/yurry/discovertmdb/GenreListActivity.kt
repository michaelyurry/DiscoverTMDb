package com.yurry.discovertmdb

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yurry.discovertmdb.model.Genre
import com.yurry.discovertmdb.presenter.TMDbPresenter
import com.yurry.discovertmdb.presenter.TMDbPresenterImpl
import kotlinx.android.synthetic.main.activity_genre_list.*

class GenreListActivity : AppCompatActivity(), GenreListView {
    private val presenter: TMDbPresenter = TMDbPresenterImpl(this)
    private lateinit var adapter: GenreRVAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genre_list)
        setRecyclerView()
        presenter.getGenreList()
    }

    override fun hideLoading() {
        movie_detail_loading.visibility = View.GONE
    }

    override fun setData(genreList: List<Genre>) {
        adapter.setGenreList(genreList)
    }

    override fun showErrorToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun showEmptyDataToast() {
        Toast.makeText(this, getString(R.string.empty_data), Toast.LENGTH_SHORT).show()
    }

    private fun setRecyclerView() {
        val linearLayoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapter = GenreRVAdapter()
        genre_recycler_view.layoutManager = linearLayoutManager
        genre_recycler_view.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(
            genre_recycler_view.context,
            LinearLayoutManager.VERTICAL
        )
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.divider))
        genre_recycler_view.addItemDecoration(dividerItemDecoration)

        adapter.setItemClickListener(object : GenreRVAdapter.ItemClickListener {
                override fun onItemClick(view: View, genre: Genre) {
                    val intent = Intent(applicationContext, MovieDiscoverListActivity::class.java)
                    intent.putExtra(Constant.GENRE_KEY, genre.id)
                    startActivity(intent)
                }
            }
        )


    }
}
