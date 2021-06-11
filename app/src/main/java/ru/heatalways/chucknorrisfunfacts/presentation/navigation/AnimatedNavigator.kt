package ru.heatalways.chucknorrisfunfacts.presentation.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Replace
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.heatalways.chucknorrisfunfacts.R

class AnimatedNavigator(
    activity: FragmentActivity,
    @IdRes containerId: Int
): AppNavigator(activity, containerId) {
    private var executeAnimation = true

    override fun applyCommand(command: Command) {
        executeAnimation = command !is Replace
        super.applyCommand(command)
    }

    override fun setupFragmentTransaction(
        screen: FragmentScreen,
        fragmentTransaction: FragmentTransaction,
        currentFragment: Fragment?,
        nextFragment: Fragment
    ) {
        if (executeAnimation) {
            fragmentTransaction.setCustomAnimations(
                R.anim.screen_slide_in_left,
                R.anim.screen_slide_out_left,
                R.anim.screen_slide_out_right,
                R.anim.screen_slide_in_right
            )
        }
    }
}