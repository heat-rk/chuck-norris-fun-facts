package ru.heatalways.chucknorrisfunfacts.presentation.screen.main

import androidx.lifecycle.SavedStateHandle
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.domain.interactors.main.*
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseMviViewModel
import ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.RandomJokeFragment
import ru.heatalways.chucknorrisfunfacts.presentation.screen.search_joke.SearchJokeFragment

class MainViewModel(
    private val savedStateHandle: SavedStateHandle
): BaseMviViewModel<
        MainAction, MainViewState,
        MainViewEffect, MainPartialState
>(MainStateReducer) {
    override val initialState: MainViewState
        get() = MainViewState()

    init {
        val isAlreadyNavigated = savedStateHandle.get<Boolean?>(ALREADY_NAVIGATED) ?: false

        if (!isAlreadyNavigated)
            setEffect(MainViewEffect.SelectFragment(SearchJokeFragment.getScreen()))
    }

    override fun handleAction(action: MainAction) {
        when (action) {
            is MainAction.OnBottomItemChange -> {
                savedStateHandle.set(ALREADY_NAVIGATED, true)
                navigateByItemId(action.itemId)
            }
        }
    }

    private fun navigateByItemId(id: Int) {
        val screen = when(id) {
            R.id.navJokeSearch -> SearchJokeFragment.getScreen()
            R.id.navJokeRandom -> RandomJokeFragment.getScreen()
            else -> null
        }

        if (screen != null)
            setEffect(MainViewEffect.SelectFragment(screen))
    }

    companion object {
        private const val ALREADY_NAVIGATED = "screen.main.already_navigated"
    }
}