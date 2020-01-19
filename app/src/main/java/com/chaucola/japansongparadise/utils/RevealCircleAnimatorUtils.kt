package com.chaucola.japansongparadise.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment

private const val CIRCULAR_SPEED = 400L

class RevealCircleAnimatorUtils(fragment: Fragment) {
    private var mSourceX: Float = -1f
    private var mSourceY: Float = -1f

    companion object {
        private const val SOURCE_X = "source_x"
        private const val SOURCE_Y = "source_y"
        private const val EXTRAS = "reveal-circle-extra"

        fun addValues(arguments: Bundle, sourceView: View?) {
            if (sourceView != null) {
                return arguments.putBundle(EXTRAS, createBundle(sourceView))
            }
        }

        private fun createBundle(sourceView: View) = Bundle().apply {
            val outRect = IntArray(2)
            sourceView.getLocationOnScreen(outRect)
            putFloat(SOURCE_X, outRect[0].toFloat() + sourceView.width / 2)
            putFloat(SOURCE_Y, outRect[1].toFloat() + sourceView.height / 2)
        }

        fun create(fragment: Fragment) = RevealCircleAnimatorUtils(fragment)
    }

    init {
        if (extractBundleValues(fragment.arguments)) {
            fragment.arguments!!.remove(EXTRAS)
        }
    }

    private fun extractBundleValues(extras: Bundle?): Boolean {
        val bundle = extras?.getBundle(EXTRAS)
        if (bundle != null) {
            mSourceX = bundle.getFloat(SOURCE_X)
            mSourceY = bundle.getFloat(SOURCE_Y)
            return true
        }
        return false
    }

    fun start(rootLayout: View, fromColor: Int? = null, targetColor: Int? = null) {
        if (mSourceX >= 0 && mSourceY >= 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            rootLayout.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
                override fun onLayoutChange(
                    rootLayout: View?,
                    p1: Int,
                    p2: Int,
                    p3: Int,
                    p4: Int,
                    p5: Int,
                    p6: Int,
                    p7: Int,
                    p8: Int
                ) {
                    startCircularAnimation(rootLayout, fromColor, targetColor)
                    rootLayout?.removeOnLayoutChangeListener(this)
                }
            })
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun startCircularAnimation(rootLayout: View?, fromColor: Int?, targetColor: Int?) {
        if (rootLayout == null) {
            return
        }
        val circularReveal =
            getCircularAnimator(rootLayout, mSourceX.toInt(), mSourceY.toInt(), CIRCULAR_SPEED)
        val animator = AnimatorSet()
        if (fromColor != null && targetColor != null) {
            val fadeAnimator =
                getColorCrossFadeAnimator(rootLayout, fromColor, targetColor, CIRCULAR_SPEED)
            animator.play(circularReveal).before(fadeAnimator)
        } else {
            animator.play(circularReveal)
        }
        animator.start()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun getCircularAnimator(
        targetView: View,
        sourceX: Int,
        sourceY: Int,
        speed: Long
    ): Animator {
        val finalRadius =
            Math.hypot(targetView.width.toDouble(), targetView.height.toDouble()).toFloat()
        return ViewAnimationUtils.createCircularReveal(
            targetView,
            sourceX,
            sourceY,
            0f,
            finalRadius
        ).apply {
            interpolator = AccelerateDecelerateInterpolator()
            duration = speed
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun getColorCrossFadeAnimator(
        targetView: View,
        fromColor: Int,
        targetColor: Int,
        speed: Long
    ): ValueAnimator {
        val colorAnimation = ValueAnimator.ofArgb(fromColor, targetColor)
        colorAnimation.interpolator = AccelerateDecelerateInterpolator()
        colorAnimation.duration = speed
        // Set new color to background smoothly
        colorAnimation.addUpdateListener { animation ->
            targetView.setBackgroundColor(animation.animatedValue as Int)
        }
        // if canceled or ended set final color
        colorAnimation.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                targetView.setBackgroundColor(targetColor)
            }

            override fun onAnimationCancel(animation: Animator) {
                targetView.setBackgroundColor(targetColor)
            }
        })
        targetView.setBackgroundColor(fromColor)
        return colorAnimation
    }
}