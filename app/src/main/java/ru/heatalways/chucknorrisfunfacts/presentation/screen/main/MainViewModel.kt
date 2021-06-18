package ru.heatalways.chucknorrisfunfacts.presentation.screen.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseViewModel
import ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.RandomJokeFragment
import ru.heatalways.chucknorrisfunfacts.presentation.screen.search_joke.SearchJokeFragment

class MainViewModel: BaseViewModel() {
    private val mCurrentScreen = MutableLiveData<FragmentScreen>()
    val currentScreen: LiveData<FragmentScreen> = mCurrentScreen

    init {
        mCurrentScreen.value = SearchJokeFragment.getScreen()
    }

    fun onMenuItemSelect(itemId: Int) {
        val screen = when(itemId) {
            R.id.navJokeSearch -> SearchJokeFragment.getScreen()
            R.id.navJokeRandom -> RandomJokeFragment.getScreen()
            else -> null
        }

        screen?.let { mCurrentScreen.value = it }
    }
}