package ru.heatalways.chucknorrisfunfacts.presentation.screen.main

import androidx.lifecycle.SavedStateHandle
import ru.heatalways.chucknorrisfunfacts.common.presentation.factories.ViewModelFactory
import ru.heatalways.chucknorrisfunfacts.core.presentation.base.MviViewModel
import ru.heatalways.chucknorrisfunfacts.random_joke.di.RandomJokeFeatureApi
import ru.heatalways.chucknorrisfunfacts.random_joke.di.RandomJokeFeatureComponentHolder
import ru.heatalways.chucknorrisfunfacts.search_joke.di.SearchJokeFeatureApi
import ru.heatalways.chucknorrisfunfacts.search_joke.di.SearchJokeFeatureComponentHolder
import ru.heatalways.chucknorrisfunfacts.settings.di.SettingsFeatureApi
import ru.heatalways.chucknorrisfunfacts.settings.di.SettingsFeatureComponentHolder
import javax.inject.Inject

class MainViewModel: MviViewModel<
        MainAction,
        MainViewState,
        MainPartialState
        >(MainStateReducer) {

    override val initialState: MainViewState get() = MainViewState()
    override fun handleAction(action: MainAction) = Unit

    override fun onCleared() {
        super.onCleared()

        // Reset feature components to prevent memory leaks
        SearchJokeFeatureComponentHolder.reset()
        RandomJokeFeatureComponentHolder.reset()
        SettingsFeatureComponentHolder.reset()
    }

    // Inject this features to be available to navigate between them
    class Factory @Inject constructor(
        searchJokeFeature: SearchJokeFeatureApi,
        randomJokeFeature: RandomJokeFeatureApi,
        settingsFeature: SettingsFeatureApi
    ): ViewModelFactory<MainViewModel> {
        override fun create(handle: SavedStateHandle) =
            MainViewModel()
    }
}