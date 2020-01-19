package com.chaucola.japansongparadise.view

interface BaseView {

    fun showError(message: String)

    fun showErrorInternet()

    fun showLoading()

    fun hideLoading()
}