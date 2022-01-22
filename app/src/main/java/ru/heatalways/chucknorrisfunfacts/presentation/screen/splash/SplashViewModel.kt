package ru.heatalways.chucknorrisfunfacts.presentation.screen.splash

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.core.base.MviViewModel
import ru.heatalways.chucknorrisfunfacts.core.viewmodels.ViewModelFactory
import ru.heatalways.chucknorrisfunfacts.domain.interactors.settings.SettingsInteractor
import ru.heatalways.chucknorrisfunfacts.core.models.AppSettings
import ru.heatalways.chucknorrisfunfacts.domain.usecases.ClearAppDataBaseUseCase
import ru.heatalways.chucknorrisfunfacts.extensions.flowTimer
import ru.heatalways.chucknorrisfunfacts.extensions.mergeWith
import ru.heatalways.chucknorrisfunfacts.presentation.util.AppUtils
import javax.inject.Inject

class SplashViewModel(
    settingsInteractor: SettingsInteractor,
    clearAppDataBaseUseCase: ClearAppDataBaseUseCase
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
                        clearAppDataBaseUseCase.execute()
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
        private val clearAppDataBaseUseCase: ClearAppDataBaseUseCase
    ): ViewModelFactory<SplashViewModel> {
        override fun create(handle: SavedStateHandle) =
            SplashViewModel(settingsInteractor, clearAppDataBaseUseCase)
    }
}