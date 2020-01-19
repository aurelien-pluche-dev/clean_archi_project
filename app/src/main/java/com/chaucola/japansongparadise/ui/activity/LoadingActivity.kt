package com.chaucola.japansongparadise.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.chaucola.domain.dbversion.interactor.GetVersion
import com.chaucola.domain.song.Song
import com.chaucola.domain.song.interactor.GetSongsFromDatabase
import com.chaucola.domain.song.interactor.SaveSongs
import com.chaucola.japansongparadise.BaseApplication
import com.chaucola.japansongparadise.R
import com.chaucola.japansongparadise.utils.getDataBase
import com.chaucola.japansongparadise.utils.isOnline
import com.chaucola.japansongparadise.utils.log
import com.chaucola.japansongparadise.utils.setDataBase
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

private const val TAG: String = "LoadingActivity"

class LoadingActivity : BaseActivity() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        downloadDatabase()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    private fun downloadDatabase() {
        if (isOnline(this)) {
            val getVersion = GetVersion((application as BaseApplication).dbVersionRepository)
            getVersion.buildUseCaseSingle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<String> {
                    override fun onSuccess(dbVersion: String) {
                        val versionLocal = getDataBase(applicationContext)
                        if (dbVersion != versionLocal) {
                            val getSongs =
                                GetSongsFromDatabase((application as BaseApplication).songRepository)
                            getSongs.buildUseCaseSingle()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(object : SingleObserver<List<Song>> {
                                    override fun onSuccess(t: List<Song>) {
                                        val saveSongs =
                                            SaveSongs((application as BaseApplication).songRepository)
                                        saveSongs.buildUseCaseSingle(t)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(object : SingleObserver<Boolean> {
                                                override fun onSuccess(t: Boolean) {
                                                    setDataBase(applicationContext, dbVersion)
                                                    openMainActivity()
                                                }

                                                override fun onSubscribe(d: Disposable) {
                                                    compositeDisposable.add(d)
                                                }

                                                override fun onError(e: Throwable) {
                                                    log(TAG, e)
                                                    showError(getString(R.string.loading_error))
                                                }
                                            })
                                    }

                                    override fun onSubscribe(d: Disposable) {
                                        compositeDisposable.add(d)
                                    }

                                    override fun onError(e: Throwable) {
                                        log(TAG, e)
                                        showError(getString(R.string.loading_error))
                                    }

                                })
                        } else {
                            openMainActivity()
                        }
                    }

                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable.add(d)
                    }

                    override fun onError(e: Throwable) {
                        log(TAG, e)
                        showError(getString(R.string.loading_error))
                    }
                })
        } else {
            showDialog()
        }
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.no_internet_title))
        builder.setMessage(getString(R.string.no_internet_message))
        builder.setPositiveButton(android.R.string.yes) { _, _ ->
            downloadDatabase()
        }
        builder.setCancelable(false)
        builder.show()
    }

    private fun openMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }

    private fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

}