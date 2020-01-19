package com.chaucola.japansongparadise.presenter

import com.chaucola.japansongparadise.view.BaseView
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import java.net.UnknownHostException

abstract class BasePresenter<C> {

    var disposable: Disposable? = null
    var view: BaseView? = null

    fun onStop() {
        disposable?.dispose()
        disposable = null
    }

    fun onDestroy() {
        onStop()
        view = null
    }

    fun showViewLoading() {
        view?.showLoading()
    }

    protected abstract fun showInView(content: C)

    protected fun defaultEntityObserver() = object : SingleObserver<C> {

        override fun onError(e: Throwable) {
            view?.let {
                hideViewLoading()
                if (e is UnknownHostException) {
                    it.showErrorInternet()
                } else {
                    it.showError("onError : " + e.message)
                }
            }
        }

        override fun onSubscribe(d: Disposable) {
            disposable = d
        }

        override fun onSuccess(@io.reactivex.annotations.NonNull entityList: C) {
            view?.let {
                hideViewLoading()
                showInView(entityList)
            }
        }

        private fun hideViewLoading() {
            view?.hideLoading()
        }
    }
}