package com.chaucola.japansongparadise.ui.fragment

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    var canTransitionFragment = false

    override fun onResume() {
        super.onResume()
        canTransitionFragment = true
    }

    override fun onPause() {
        super.onPause()
        canTransitionFragment = false
    }
}