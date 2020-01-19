package com.chaucola.japansongparadise.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chaucola.domain.favorite.interactor.AddFavorite
import com.chaucola.japansongparadise.BaseApplication
import com.chaucola.japansongparadise.R
import com.chaucola.japansongparadise.utils.log
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_add_favorite_dialog.*

private const val TAG: String = "AddFavoriteDialogActivity"

class AddFavoriteDialogActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_favorite_dialog)

        add_favorite_no.setOnClickListener { finish() }

        add_favorite_yes.setOnClickListener {
            addFavorite()
        }
    }

    private fun addFavorite() {
        val newPlaylist = add_favorite_edittext.text.toString()
        if (newPlaylist.isNotEmpty()) {
            val addFavorite = AddFavorite((application as BaseApplication).favoriteRepository)
            addFavorite.buildUseCaseSingle(newPlaylist)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<Boolean> {
                    override fun onSuccess(t: Boolean) {
                        finish()
                    }

                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable.add(d)
                    }

                    override fun onError(e: Throwable) {
                        log(TAG, e)
                    }
                })
        } else {
            add_favorite_text_input_layout.error = getString(R.string.add_favorite_error_empty)
        }
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}