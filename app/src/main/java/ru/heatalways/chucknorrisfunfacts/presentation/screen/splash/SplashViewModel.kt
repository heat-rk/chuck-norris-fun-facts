package ru.heatalways.chucknorrisfunfacts.presentation.screen.splash

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import ru.heatalways.chucknorrisfunfacts.common.extensions.flowTimer
import ru.heatalways.chucknorrisfunfacts.common.extensions.mergeWith
import ru.heatalways.chucknorrisfunfacts.common.presentation.factories.ViewModelFactory
import ru.heatalways.chucknorrisfunfacts.common.presentation.utils.AppUtils
import ru.heatalways.chucknorrisfunfacts.core.general.AppSettings
import ru.heatalways.chucknorrisfunfacts.core.presentation.base.MviViewModel
import ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.domain.interactors.chuck_norris_jokes.ChuckNorrisJokesInteractor
import ru.heatalways.chucknorrisfunfacts.core_settings.domain.interactors.settings.SettingsInteractor
import ru.heatalways.navigation.R
import javax.inject.Inject

class SplashViewModel(
    settingsInteractor: SettingsInteractor,
    chuckNorrisJokesInteractor: ChuckNorrisJokesInteractor
): MviViewModel<
        SplashAction,
        SplashViewState,
        SplashPartialState
        >(reducer = SplashReducer) {
    override val initialState get() = SplashViewState()

    init {
        flowTimer(SPLASH_SCREEN_DURATION)
            .mergeWith(settingsInteractor.settings.take(1))
            .onEach {
                if (it is AppSettings) {
                    AppUtils.setDefaultNightMode(it.isNightModeEnabled)

                    if (it.isClearCacheAfterExitEnabled)
                        chuckNorrisJokesInteractor.removeSavedJokes()
                            .launchIn(viewModelScope)
                }
            }
            .onCompletion {
                navigator { navigate(R.id.action_splashFragment_to_mainFragment) }
            }
            .launchIn(viewModelScope)
    }

    override fun handleAction(action: SplashAction) = Unit

    companion object {
        private const val SPLASH_SCREEN_DURATION = 1000L
    }

    class Factory @Inject constructor(
        private val settingsInteractor: SettingsInteractor,
        private val chuckNorrisJokesInteractor: ChuckNorrisJokesInteractor
    ): ViewModelFactory<SplashViewModel> {
        override fun create(handle: SavedStateHandle) =
            SplashViewModel(settingsInteractor, chuckNorrisJokesInteractor)
    }
}