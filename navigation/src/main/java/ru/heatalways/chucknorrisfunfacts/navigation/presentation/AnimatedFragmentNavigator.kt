package ru.heatalways.navigation.navigation.presentation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.navOptions
import ru.heatalways.chucknorrisfunfacts.navigation.R


/**
 * Needs to replace FragmentNavigator and replacing is done with name in annotation.
 * Navigation method will use defaults for fragments transitions animations.
 */
@Navigator.Name("fragment")
class AnimatedFragmentNavigator(
    context: Context,
    manager: FragmentManager,
    containerId: Int
) : FragmentNavigator(context, manager, containerId) {

    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ) = super.navigate(
        destination,
        args,
        if (navigatorExtras != null) navOptions
        else navOptions.fillEmptyAnimationsWithDefaults(),
        navigatorExtras
    )


    private fun NavOptions?.fillEmptyAnimationsWithDefaults(): NavOptions =
        this?.copyNavOptionsWithDefaultAnimations() ?: defaultNavOptions

    private fun NavOptions.copyNavOptionsWithDefaultAnimations(): NavOptions =
        let { originalNavOptions ->
            navOptions {
                launchSingleTop = originalNavOptions.shouldLaunchSingleTop()
                popUpTo(originalNavOptions.popUpTo) {
                    inclusive = originalNavOptions.isPopUpToInclusive
                }
                anim {
                    enter =
                        if (originalNavOptions.enterAnim == emptyNavOptions.enterAnim) defaultNavOptions.enterAnim
                        else originalNavOptions.enterAnim
                    exit =
                        if (originalNavOptions.exitAnim == emptyNavOptions.exitAnim) defaultNavOptions.exitAnim
                        else originalNavOptions.exitAnim
                    popEnter =
                        if (originalNavOptions.popEnterAnim == emptyNavOptions.popEnterAnim) defaultNavOptions.popEnterAnim
                        else originalNavOptions.popEnterAnim
                    popExit =
                        if (originalNavOptions.popExitAnim == emptyNavOptions.popExitAnim) defaultNavOptions.popExitAnim
                        else originalNavOptions.popExitAnim
                }
            }
        }

    companion object {
        private val defaultNavOptions = navOptions {
            anim {
                enter = R.anim.screen_slide_in_left
                exit = R.anim.screen_slide_out_left
                popEnter = R.anim.screen_slide_out_right
                popExit = R.anim.screen_slide_in_right
            }
        }

        private val emptyNavOptions = navOptions {}
    }
}