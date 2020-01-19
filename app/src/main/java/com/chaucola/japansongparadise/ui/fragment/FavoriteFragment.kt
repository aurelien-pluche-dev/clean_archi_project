package com.chaucola.japansongparadise.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import com.chaucola.domain.favorite.interactor.GetFavorites
import com.chaucola.domain.favorite.interactor.RemoveFavorite
import com.chaucola.japansongparadise.BaseApplication
import com.chaucola.japansongparadise.R
import com.chaucola.japansongparadise.model.FavoriteViewModel
import com.chaucola.japansongparadise.model.Navigation
import com.chaucola.japansongparadise.model.TagFragment
import com.chaucola.japansongparadise.presenter.FavoritePresenter
import com.chaucola.japansongparadise.ui.activity.AddFavoriteDialogActivity
import com.chaucola.japansongparadise.ui.activity.AddSongToFavoriteActivity
import com.chaucola.japansongparadise.ui.activity.ListSongFavoriteActivity
import com.chaucola.japansongparadise.ui.adapter.ListFavoriteAdapter
import com.chaucola.japansongparadise.utils.RevealCircleAnimatorUtils
import com.chaucola.japansongparadise.view.ListFavoriteView
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_favorite.*

private const val TAG: String = "FavoriteFragment"

class FavoriteFragment : BaseFragment(), TagFragment, ListFavoriteView {

    private val compositeDisposable = CompositeDisposable()

    private val favoritePresenter: FavoritePresenter by lazy {
        val getFavorites =
            GetFavorites((activity!!.application as BaseApplication).favoriteRepository)
        FavoritePresenter(getFavorites)
    }

    private val favoriteListAdapter: ListFavoriteAdapter by lazy { ListFavoriteAdapter(true) }
    private val onItemClickListener: ListFavoriteAdapter.OnItemClickListener by lazy {
        object : ListFavoriteAdapter.OnItemClickListener {
            override fun onDelete(favoriteId: Int) {
                removeFavorite(favoriteId)
            }

            override fun onDetails(favoriteId: Int, favoriteTitle: String) {
                val bundle = ListSongFavoriteActivity.buildBundle(favoriteId, favoriteTitle)
                val intent = Intent(context, ListSongFavoriteActivity::class.java)
                intent.putExtras(bundle)
                activity?.startActivity(intent)
                activity?.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }
    }

    companion object {
        fun newInstance(sourceView: View): FavoriteFragment = FavoriteFragment().apply {
            arguments = Bundle().apply {
                RevealCircleAnimatorUtils.addValues(this, sourceView)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_favorite, container, false)
        RevealCircleAnimatorUtils
            .create(this)
            .start(root)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_list_favorite.adapter = favoriteListAdapter
        favoriteListAdapter.setOnItemClickListener(onItemClickListener)
        recycler_list_favorite.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )

        floatingButton.show()
        floatingButton.setOnClickListener {
            val intent = Intent(requireContext(), AddFavoriteDialogActivity::class.java)
            startActivity(intent)
        }
    }

    override fun getFragmentTag(): String {
        return TAG
    }

    override fun newNavigation(navigation: Navigation) {

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

    private fun removeFavorite(favoriteId: Int) {
        val removeFavorite =
            RemoveFavorite((activity?.application as BaseApplication).favoriteRepository)
        removeFavorite.buildUseCaseSingle(favoriteId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Boolean> {
                override fun onSuccess(t: Boolean) {
                    onResume()
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onError(e: Throwable) {
                }
            })
    }

}