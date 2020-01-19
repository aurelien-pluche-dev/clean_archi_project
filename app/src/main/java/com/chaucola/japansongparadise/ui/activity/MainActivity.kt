package com.chaucola.japansongparadise.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.chaucola.japansongparadise.R
import com.chaucola.japansongparadise.model.Navigation
import com.chaucola.japansongparadise.model.TagFragment
import com.chaucola.japansongparadise.model.TransitionAnimator
import com.chaucola.japansongparadise.ui.fragment.FavoriteFragment
import com.chaucola.japansongparadise.ui.fragment.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener{

    private lateinit var navigation: Navigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_bottom_navigation_view.setOnNavigationItemSelectedListener(this)
        displayFragmentFromNavigation(
            Navigation.NavigationBuilder()
                .setTabId(R.id.action_home)
                .build()
        )
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        displayFragmentFromNavigation(
            Navigation.NavigationBuilder()
                .setTabId(item.itemId)
                .build()
        )
        return true
    }

    private fun displayFragmentFromNavigation(navigation: Navigation) {
        this.navigation = navigation

        val menuItem = main_bottom_navigation_view.menu.findItem(navigation.getTabId())
        if (menuItem != null) {
            menuItem.isChecked = true
        }

        val toFragment: Fragment = when (navigation.getTabId()) {
            R.id.action_favorite -> {
                FavoriteFragment.newInstance(main_bottom_navigation_view.findViewById(R.id.action_favorite))
            }
            else -> {
                HomeFragment.newInstance(main_bottom_navigation_view.findViewById(R.id.action_home))
            }
        }

        //We need to clear back stack when tab changing to have a good back behavior
        supportFragmentManager.fragments.forEach {
            if (it is TransitionAnimator) {
                it.disableTransitionAnimation()
            }
        }
        val nbFragment = supportFragmentManager.backStackEntryCount
        for (i in 0 until nbFragment) {
            supportFragmentManager.popBackStackImmediate()
        }

        val tag = (toFragment as TagFragment).getFragmentTag() + navigation.toString()
        val fragment = supportFragmentManager.findFragmentByTag(tag)
        if (canTransitionFragment) {
            if (fragment == null || !fragment.isVisible) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_container, toFragment, tag).commit()
            } else if (fragment.isVisible) {
                (fragment as TagFragment).newNavigation(navigation)
            }
        }
    }
}
