package com.ddd4.synesthesia.beer.presentation.ui.main.view

import android.os.Bundle
import android.view.MenuItem
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ActivityMainBinding
import com.ddd4.synesthesia.beer.presentation.base.BaseActivity
import com.ddd4.synesthesia.beer.presentation.ui.main.KeepStateNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val navHostFragment: NavHostFragment by lazy { supportFragmentManager.findFragmentById(R.id.nav_main_container) as NavHostFragment }
    private val navController: NavController by lazy { navHostFragment.navController }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        showRecentlyVisitTime()
        initBottomNavigationController()
        initBottomNavigationListener()
    }

    override fun onBackPressed() {
        navController.run {
            when (currentDestination?.id) {
                R.id.nav_home -> {
                    isInTimeInput {
                        finishAffinity()
                    }
                }
                R.id.nav_search,
                R.id.nav_mypage -> {
                    binding.navBottomView.selectedItemId = graph.startDestination
                }
                else -> {
                    // do nothing
                }
            }
        }
    }

    /**
     * bottom navigation에 따라 status bar color를 변경한다.
     */
    private fun setStatusBarColor(
        item: MenuItem
    ) {
        when (item.itemId) {
            R.id.nav_home,
            R.id.nav_mypage -> {
                window?.statusBarColor = getColor(R.color.subBackgroundTint)
            }
            R.id.nav_search -> {
                window?.statusBarColor = getColor(R.color.backgroundTint)
            }
        }
    }

    private fun initBottomNavigationListener() {
        binding.navBottomView.run {
            setupWithNavController(navController)

            /**
             * bottom navigation listener
             */
            setOnItemSelectedListener {
                setStatusBarColor(it)
                navController.navigate(
                    it.itemId,
                    bundleOf(
                        KEY_CURRENT_NAVIGATION_POSITION to navigationPosition(navController.currentDestination?.id),
                        KEY_DESTINATION_NAVIGATION_POSITION to navigationPosition(it.itemId),
                    )
                )
                navController.currentDestination?.id != it.itemId
            }
        }
    }

    private fun navigationPosition(itemId: Int?): Int {
        return when (itemId) {
            R.id.nav_home -> {
                0
            }
            R.id.nav_search -> {
                1
            }
            R.id.nav_mypage -> {
                2
            }
            else -> {
                -1
            }
        }
    }

    private fun initBottomNavigationController() {
        val keepNavController = KeepStateNavigator(
            context = this@MainActivity,
            manager = navHostFragment.childFragmentManager,
            R.id.nav_main_container
        )
        navController.navigatorProvider.addNavigator(keepNavController)
        navController.setGraph(R.navigation.home)
    }

    companion object {
        const val KEY_CURRENT_NAVIGATION_POSITION = "current_navigation_position"
        const val KEY_DESTINATION_NAVIGATION_POSITION = "destination_navigation_position"
    }
}