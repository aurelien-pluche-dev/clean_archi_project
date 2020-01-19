package com.chaucola.japansongparadise.ui.activity

import android.animation.ValueAnimator
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.chaucola.domain.song.interactor.GetDetailSong
import com.chaucola.japansongparadise.BaseApplication
import com.chaucola.japansongparadise.R
import com.chaucola.japansongparadise.model.SongViewModel
import com.chaucola.japansongparadise.presenter.SongDetailPresenter
import com.chaucola.japansongparadise.utils.log
import com.chaucola.japansongparadise.view.SongDetailView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_song_player.*
import java.io.IOException

private const val TAG = "SongPlayerActivity"

private const val EXTRA_SONG_ID = "EXTRA_SONG"

private const val UPDATE_SEEK_BAR = 1000
private const val DURATION_ANIMATOR = 12000L

class SongPlayerActivity : AppCompatActivity(), SongDetailView, MediaPlayer.OnPreparedListener,
    MediaPlayer.OnCompletionListener,
    SeekBar.OnSeekBarChangeListener {

    private val songId: Int  by lazy { intent.getIntExtra(EXTRA_SONG_ID, -1) }

    private var currentPosition: Int = 0

    private var mp: MediaPlayer? = null
    private var handler: Handler? = null

    private var valueAnimator: ValueAnimator? = null

    private lateinit var song: SongViewModel
    private var isPrepared: Boolean = false

    private val songDetailPresenter: SongDetailPresenter by lazy {
        val getSong = GetDetailSong((application as BaseApplication).songRepository)
        SongDetailPresenter(getSong)
    }

    private val mUpdateCounters = object : Runnable {
        override fun run() {
            if (!isPrepared) {
                return
            }

            if (mp!!.isPlaying) {
                currentPosition++
            }
            if ((currentPosition * 1000) >= mp!!.duration) {
                mp?.seekTo(0)
                currentPosition = 0
            }

            updateUi()
            if (handler != null) {
                handler?.postDelayed(this, UPDATE_SEEK_BAR.toLong())
            }
        }
    }

    companion object {
        fun buildBundle(songId: Int): Bundle {
            val b = Bundle()
            b.putInt(EXTRA_SONG_ID, songId)
            return b
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_player)

        handler = Handler()

        mp = MediaPlayer()
        mp!!.setOnPreparedListener(this)
        mp!!.setOnCompletionListener(this)
        seek_bar.setOnSeekBarChangeListener(this)

        status_player_button.setOnClickListener {
            handlePlayButton()
        }

        close_player.setOnClickListener { finish() }
    }

    override fun renderSong(song: SongViewModel) {
        this.song = song

        title_song.text = song.title

        if (!isPrepared) {
            prepareMediaPlayer()
        }
    }

    override fun onPrepared(p0: MediaPlayer?) {
        isPrepared = true

        seek_bar.progress = 0
        seek_bar.max = getDuration()

        left_text_player.text = "0"
        right_text_player.text = convertSecondsToHMmSs(getDuration())

        mp!!.start()
        handler?.post(mUpdateCounters)

        Picasso.get().load(song.img).fit().into(song_image)
        player_view_container.visibility = View.VISIBLE
        progress_bar.visibility = View.GONE

        valueAnimator = ValueAnimator.ofFloat(0f, 360f)
        valueAnimator!!.addUpdateListener {
            val value = it.animatedValue as Float
            song_image.rotation = value
        }

        valueAnimator!!.interpolator = LinearInterpolator()
        valueAnimator!!.duration = DURATION_ANIMATOR
        valueAnimator!!.repeatCount = ValueAnimator.INFINITE
        valueAnimator!!.start()
    }

    override fun onCompletion(p0: MediaPlayer?) {
        seek_bar.progress = 0
        mp?.seekTo(0)
        currentPosition = 0
        updateUi()
    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        if (isPrepared && p2) {
            mp!!.seekTo(p1 * 1000)
            currentPosition = p1
            updateUi()
        }
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {

    }

    override fun onStopTrackingTouch(p0: SeekBar?) {

    }

    override fun showError(message: String) {

    }

    override fun showErrorInternet() {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onStart() {
        super.onStart()
        songDetailPresenter.initialize(this, songId)
    }

    override fun onStop() {
        songDetailPresenter.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        songDetailPresenter.onDestroy()
        handler?.removeCallbacks(mUpdateCounters)
        seek_bar.setOnSeekBarChangeListener(null)
        mp?.setOnPreparedListener(null)
        mp?.setOnCompletionListener(null)
        mp?.release()
        super.onDestroy()
    }

    private fun prepareMediaPlayer() {
        try {
            mp!!.reset()
            mp!!.setDataSource(song.url)
            mp!!.prepareAsync()

        } catch (e: IOException) {
            log(TAG, e)
        } catch (e: IllegalStateException) {
            log(TAG, e)
        }
    }

    private fun updateUi() {
        seek_bar.progress = currentPosition
        left_text_player.text = convertSecondsToHMmSs(currentPosition)
    }

    private fun getDuration(): Int {
        return if (!isPrepared) {
            -1
        } else
            return mp!!.duration / 1000
    }

    private fun handlePlayButton() {
        if (isPrepared) {
            mp?.let {
                if (mp!!.isPlaying) {
                    mp!!.pause()
                    status_player_button.setImageResource(R.drawable.ic_play_white)
                    valueAnimator?.pause()
                } else {
                    mp!!.start()
                    status_player_button.setImageResource(R.drawable.ic_pause_24dp)
                    valueAnimator?.start()
                }
            }
        }
    }

    private fun convertSecondsToHMmSs(seconds: Int): String {
        val s = seconds % 60
        val m = seconds / 60 % 60
        return if (seconds < 10) {
            String.format("%01d", s)
        } else if (seconds < 60) {
            String.format("%02d", s)
        } else {
            String.format("%02d:%02d", m, s)
        }
    }

}