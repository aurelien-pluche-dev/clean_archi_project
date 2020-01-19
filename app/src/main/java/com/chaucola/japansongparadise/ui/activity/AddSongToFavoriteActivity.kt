package com.chaucola.japansongparadise.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import com.chaucola.domain.favorite.interactor.GetFavorites
import com.chaucola.domain.favorite.interactor.UpdateFavoris
import com.chaucola.japansongparadise.BaseApplication
import com.chaucola.japansongparadise.R
import com.chaucola.japansongparadise.model.FavoriteViewModel
import com.chaucola.japansongparadise.presenter.FavoritePresenter
import com.chaucola.japansongparadise.ui.adapter.ListFavoriteAdapter
import com.chaucola.japansongparadise.utils.log
import com.chaucola.japansongparadise.view.ListFavoriteView
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_favorite.*

private const val TAG = "AddSongToFavoriteActivity"
private const val EXTRA_SONG_ID = "EXTRA_SONG"

class AddSongToFavoriteActivity : AppCompatActivity(), ListFavoriteView {

    private val songId: Int  by lazy { intent.getIntExtra(EXTRA_SONG_ID, -1) }

    private val compositeDisposable = CompositeDisposable()

    private val onItemClickListener: ListFavoriteAdapter.OnItemClickListener by lazy {
        object : ListFavoriteAdapter.OnItemClickListener {
            override fun onDelete(favoriteId: Int) {
                //nothing
            }

            override fun onDetails(favoriteId: Int, favoriteTitle: String) {
                val updateFavoris =
                    UpdateFavoris((application as BaseApplication).favoriteRepository)
                updateFavoris.buildUseCaseSingle(favoriteId, songId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : SingleObserver<Boolean> {
                        override fun onSuccess(t: Boolean) {
                            Toast.makeText(
                                applicationContext,
                                getString(R.string.add_song_message),
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        }

                        override fun onSubscribe(d: Disposable) {
                            compositeDisposable.add(d)
                        }

                        override fun onError(e: Throwable) {
                            log(TAG, e)
                        }
                    })
            }
        }
    }

    private val favoritePresenter: FavoritePresenter by lazy {
        val getFavorites =
            GetFavorites((application as BaseApplication).favoriteRepository)
        FavoritePresenter(getFavorites)
    }

    private val favoriteListAdapter: ListFavoriteAdapter by lazy { ListFavoriteAdapter(false) }

    companion object {
        fun buildBundle(songId: Int): Bundle {
            val b = Bundle()
            b.putInt(EXTRA_SONG_ID, songId)
            return b
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_song_favorite)

        recycler_list_favorite.adapter = favoriteListAdapter
        favoriteListAdapter.setOnItemClickListener(onItemClickListener)
        recycler_list_favorite.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        floatingButton.show()
        floatingButton.setOnClickListener {
            val intent = Intent(this, AddFavoriteDialogActivity::class.java)
            startActivity(intent)
        }
    }

    override fun renderFavorites(favorites: List<FavoriteViewModel>) {
        favoriteListAdapter.setDataList(favorites)
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
        favoritePresenter.initialize(this)
    }

    override fun onStop() {
        favoritePresenter.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        favoritePresenter.onDestroy()
        super.onDestroy()
    }
}