package ru.heatalways.chucknorrisfunfacts.presentation.screen.main

import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.domain.interactors.main.*
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseMviViewModel
import ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.RandomJokeFragment
import ru.heatalways.chucknorrisfunfacts.presentation.screen.search_joke.SearchJokeFragment

class MainViewModel: BaseMviViewModel<
        MainAction, MainViewState,
        MainViewEffect, MainPartialState
>(MainStateReducer) {
    override val initialState: MainViewState
        get() = MainViewState()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        setEffect(MainViewEffect.SelectFragment(SearchJokeFragment.getScreen()))
    }

    override fun handleAction(action: MainAction) {
        when (action) {
            is MainAction.OnBottomItemChange -> {
                val screen = when(action.itemId) {
                    R.id.navJokeSearch -> SearchJokeFragment.getScreen()
                    R.id.navJokeRandom -> RandomJokeFragment.getScreen()
                    else -> null
                }

                if (screen != null)
                    setEffect(MainViewEffect.SelectFragment(screen))
            }
        }
    }
}