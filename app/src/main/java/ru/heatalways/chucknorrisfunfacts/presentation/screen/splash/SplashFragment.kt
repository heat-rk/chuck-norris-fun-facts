package ru.heatalways.chucknorrisfunfacts.presentation.screen.splash

import android.os.Bundle
import androidx.fragment.app.viewModels
import kotlinx.coroutines.flow.emptyFlow
import ru.heatalways.chucknorrisfunfacts.common.presentation.factories.GenericSavedStateViewModelFactory
import ru.heatalways.chucknorrisfunfacts.core.presentation.base.MviFragment
import ru.heatalways.chucknorrisfunfacts.di.AppComponent
import ru.heatalways.navigation.R
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
        AppComponent.get().inject(this)
    }

    override fun renderState(state: SplashViewState) = Unit

}