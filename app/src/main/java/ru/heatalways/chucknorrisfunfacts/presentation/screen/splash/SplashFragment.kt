package ru.heatalways.chucknorrisfunfacts.presentation.screen.splash

import androidx.fragment.app.viewModels
import kotlinx.coroutines.flow.emptyFlow
import ru.heatalways.chucknorrisfunfacts.databinding.FragmentSplashBinding
import ru.heatalways.chucknorrisfunfacts.presentation.base.BindingMviFragment

class SplashFragment: BindingMviFragment<
        FragmentSplashBinding, SplashAction, SplashViewState
>(FragmentSplashBinding::inflate) {

    override val viewModel: SplashViewModel by viewModels()

    override val actions get() = emptyFlow<SplashAction>()

    override fun renderState(state: SplashViewState) = Unit
}