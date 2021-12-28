package ru.heatalways.chucknorrisfunfacts.presentation.screen.splash

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.emptyFlow
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.appComponent
import ru.heatalways.chucknorrisfunfacts.core.viewmodels.GenericSavedStateViewModelFactory
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviFragment
import javax.inject.Inject

class SplashFragment: MviFragment<
        SplashAction,
        SplashViewState
>(R.layout.fragment_splash) {

    @Inject
    lateinit var viewModelFactory: SplashViewModel.Factory

    override val viewModel: SplashViewModel by viewModels {
        GenericSavedStateViewModelFactory(viewModelFactory, this)
    }

    override val actions get() = emptyFlow<SplashAction>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireContext().appComponent.inject(this)
    }

    override fun renderState(state: SplashViewState) = Unit

}