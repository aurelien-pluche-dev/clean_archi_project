package com.chaucola.japansongparadise.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import com.chaucola.domain.favorite.interactor.GetFavorite
import com.chaucola.domain.song.interactor.GetSongsFromFavorite
import com.chaucola.japansongparadise.BaseApplication
import com.chaucola.japansongparadise.R
import com.chaucola.japansongparadise.model.FavoriteViewModel
import com.chaucola.japansongparadise.model.SongViewModel
import com.chaucola.japansongparadise.presenter.FavoriteDetailPresenter
import com.chaucola.japansongparadise.presenter.SongByFavoritePresenter
import com.chaucola.japansongparadise.ui.adapter.ListSongAdapter
import com.chaucola.japansongparadise.view.FavoriteDetailView
import com.chaucola.japansongparadise.view.ListSongView
import kotlinx.android.synthetic.main.activity_list_song_favorite.*
import kotlinx.android.synthetic.main.fragment_home.recycler_list_song

private const val EXTRA_FAVORITE_ID = "EXTRA_FAVORITE"
private const val EXTRA_FAVORITE_TITLE = "EXTRA_FAVORITE_TITLE"

class ListSongFavoriteActivity : AppCompatActivity(), FavoriteDetailView, ListSongView {

    private val favoriteId: Int  by lazy { intent.getIntExtra(EXTRA_FAVORITE_ID, -1) }
    private val favoriteTitle: String  by lazy { intent.getStringExtra(EXTRA_FAVORITE_TITLE) }

    private val songListAdapter: ListSongAdapter by lazy { ListSongAdapter(false) }
    private val onItemClickListener: ListSongAdapter.OnItemClickListener by lazy {
        object : ListSongAdapter.OnItemClickListener {
            override fun onPlay(song: SongViewModel) {
                val bundle = SongPlayerActivity.buildBundle(song.id)
                val intent = Intent(applicationContext, SongPlayerActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }

            override fun onAdd(song: SongViewModel) {
                //nothing
            }
        }
    }

    private val favoritePresenter: FavoriteDetailPresenter by lazy {
        val getFavorite =
            GetFavorite((application as BaseApplication).favoriteRepository)
        FavoriteDetailPresenter(getFavorite)
    }

    private val getSongsPresenter: SongByFavoritePresenter by lazy {
        val getSongsFromIds = GetSongsFromFavorite((application as BaseApplication).songRepository)
        SongByFavoritePresenter(getSongsFromIds)
    }

    companion object {
        fun buildBundle(favoriteId: Int, favoriteTitle: String): Bundle {
            val b = Bundle()
            b.putInt(EXTRA_FAVORITE_ID, favoriteId)
            b.putString(EXTRA_FAVORITE_TITLE, favoriteTitle)
            return b
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_song_favorite)

        list_favorite_title.text = favoriteTitle

        recycler_list_song.adapter = songListAdapter
        songListAdapter.setOnItemClickListener(onItemClickListener)
        recycler_list_song.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        back.setOnClickListener { finish() }
    }

    override fun renderFavorite(favorite: FavoriteViewModel) {
        getSongsPresenter.initialize(this, favorite.listSong)
    }

    override fun renderSongs(songs: List<SongViewModel>) {
        songListAdapter.setDataList(songs)
    }

    override fun showError(message: String) {

    }

    override fun showErrorInternet() {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onResume() {
        super.onResume()
        favoritePresenter.initialize(this, favoriteId)
    }

    override fun onStop() {
        favoritePresenter.onStop()
        getSongsPresenter.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        favoritePresenter.onDestroy()
        getSongsPresenter.onDestroy()
        super.onDestroy()
    }
}