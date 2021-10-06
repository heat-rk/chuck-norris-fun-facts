package ru.heatalways.chucknorrisfunfacts.presentation.navigation

import android.os.Bundle
import androidx.annotation.NavigationRes
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

class AnimatedNavHostFragment: NavHostFragment() {


    override fun onCreateNavController(navController: NavController) {
        super.onCreateNavController(navController)
        navController.navigatorProvider.addNavigator(
            AnimatedFragmentNavigator(requireContext(), childFragmentManager, id)
        )
    }

    companion object {
        fun createNavHost(@NavigationRes graphResId: Int): NavHostFragment {
            var b: Bundle? = null
            if (graphResId != 0) {
                b = Bundle()
                b.putInt("android-support-nav:fragment:graphId", graphResId)
            }

            val result = AnimatedNavHostFragment()
            if (b != null) {
                result.arguments = b
            }
            return result
        }
    }
}