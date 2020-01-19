package com.chaucola.japansongparadise.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import com.chaucola.japansongparadise.model.Navigation
import com.chaucola.japansongparadise.model.TagFragment
import com.chaucola.japansongparadise.utils.RevealCircleAnimatorUtils
import com.chaucola.domain.song.interactor.GetSongs
import com.chaucola.japansongparadise.BaseApplication
import com.chaucola.japansongparadise.model.SongViewModel
import com.chaucola.japansongparadise.presenter.SongPresenter
import com.chaucola.japansongparadise.ui.activity.AddSongToFavoriteActivity
import com.chaucola.japansongparadise.ui.activity.SongPlayerActivity
import com.chaucola.japansongparadise.ui.adapter.ListSongAdapter
import com.chaucola.japansongparadise.view.ListSongView
import kotlinx.android.synthetic.main.fragment_home.*

private const val TAG: String = "HomeFragment"

class HomeFragment : BaseFragment(), TagFragment, ListSongView {

    private val listSongPresenter: SongPresenter by lazy {
        val getSongs = GetSongs((activity?.application as BaseApplication).songRepository)
        SongPresenter(getSongs)
    }

    private val songListAdapter: ListSongAdapter by lazy { ListSongAdapter(true) }
    private val onItemClickListener: ListSongAdapter.OnItemClickListener by lazy {
        object : ListSongAdapter.OnItemClickListener {
            override fun onPlay(song: SongViewModel) {
                val bundle = SongPlayerActivity.buildBundle(song.id)
                val intent = Intent(requireContext(), SongPlayerActivity::class.java)
                intent.putExtras(bundle)
                activity?.startActivity(intent)
                activity?.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }

            override fun onAdd(song: SongViewModel) {
                val bundle = AddSongToFavoriteActivity.buildBundle(song.id)
                val intent = Intent(context, AddSongToFavoriteActivity::class.java)
                intent.putExtras(bundle)
                activity?.startActivity(intent)
            }
        }
    }

    companion object {
        fun newInstance(sourceView: View): HomeFragment = HomeFragment().apply {
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
        val root = inflater.inflate(
            com.chaucola.japansongparadise.R.layout.fragment_home,
            container,
            false
        )
        RevealCircleAnimatorUtils
            .create(this)
            .start(root)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_list_song.adapter = songListAdapter
        songListAdapter.setOnItemClickListener(onItemClickListener)
        recycler_list_song.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
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

    override fun getFragmentTag(): String {
        return TAG
    }

    override fun newNavigation(navigation: Navigation) {

    }

    override fun onStart() {
        super.onStart()
        listSongPresenter.initialize(this)
    }

    override fun onStop() {
        listSongPresenter.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        listSongPresenter.onDestroy()
        super.onDestroy()
    }
}