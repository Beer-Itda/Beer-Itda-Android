package com.hjiee.presentation.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import com.hjiee.presentation.R
import com.hjiee.presentation.ui.main.view.MainActivity.Companion.KEY_CURRENT_NAVIGATION_POSITION
import com.hjiee.presentation.ui.main.view.MainActivity.Companion.KEY_DESTINATION_NAVIGATION_POSITION
import com.hjiee.core.ext.orZero


@Navigator.Name("KeepStateFragment") // `keep_state_fragment` is used in navigation xml
class KeepStateNavigator(
    private val context: Context,
    private val manager: FragmentManager, // Should pass childFragmentManager.
    private val containerId: Int
) : FragmentNavigator(context, manager, containerId) {

    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ): NavDestination {
        val tag = destination.id.toString()
        var destinationFragment = manager.findFragmentByTag(tag)
        val transaction = manager.beginTransaction().apply {
            setAnimateTransition(args, this)
        }

        manager.primaryNavigationFragment?.let { currentFragment ->
            transaction.detach(currentFragment)
        }

        if (destinationFragment == null) {
            val className = destination.className
            destinationFragment = manager.fragmentFactory.instantiate(context.classLoader, className)
            transaction.add(containerId, destinationFragment, tag)
        } else {
            transaction.attach(destinationFragment)
        }

        transaction.setPrimaryNavigationFragment(destinationFragment)
        transaction.setReorderingAllowed(true)
        transaction.commitNow()

        return destination
    }

    /** bottom navigation animate transaction */
    private fun setAnimateTransition(
        args: Bundle?,
        transaction: FragmentTransaction
    ) {
        val currentPosition = args?.getInt(KEY_CURRENT_NAVIGATION_POSITION).orZero()
        val destinationPosition = args?.getInt(KEY_DESTINATION_NAVIGATION_POSITION).orZero()

        if (currentPosition < destinationPosition) {
            transaction.setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
        } else {
            transaction.setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
        }
    }
}