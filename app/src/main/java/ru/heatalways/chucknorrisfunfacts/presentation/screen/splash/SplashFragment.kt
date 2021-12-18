package ru.heatalways.chucknorrisfunfacts.presentation.screen.splash

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.emptyFlow
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.databinding.FragmentSplashBinding
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviFragment

class SplashFragment: MviFragment<
        SplashAction,
        SplashViewState
>(R.layout.fragment_splash) {

    override val viewModel: SplashViewModel by viewModels()
    private val binding by viewBinding(FragmentSplashBinding::bind)

    override val actions get() = emptyFlow<SplashAction>()

    override fun renderState(state: SplashViewState) = Unit

}