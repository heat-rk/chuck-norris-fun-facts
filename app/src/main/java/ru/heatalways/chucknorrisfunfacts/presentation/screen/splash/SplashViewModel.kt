package ru.heatalways.chucknorrisfunfacts.presentation.screen.splash

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(): MviViewModel<
        SplashAction, SplashViewState, SplashPartialState
>(reducer = SplashReducer) {
    override val initialState get() = SplashViewState()

    init {
        viewModelScope.launch {
            delay(SPLASH_SCREEN_DURATION)
            navigator { navigate(R.id.action_splashFragment_to_mainFragment) }
        }
    }

    override fun handleAction(action: SplashAction) = Unit

    companion object {
        private const val SPLASH_SCREEN_DURATION = 1000L
    }
}