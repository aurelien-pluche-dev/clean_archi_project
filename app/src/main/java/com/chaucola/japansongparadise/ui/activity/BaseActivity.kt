package com.chaucola.japansongparadise.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    var canTransitionFragment = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        canTransitionFragment = true
    }

    override fun onNewIntent(intent: Intent?) {
        canTransitionFragment = true
        super.onNewIntent(intent)
    }

    override fun onResume() {
        canTransitionFragment = true
        super.onResume()
    }

    override fun onPause() {
        canTransitionFragment = false
        super.onPause()
    }

}