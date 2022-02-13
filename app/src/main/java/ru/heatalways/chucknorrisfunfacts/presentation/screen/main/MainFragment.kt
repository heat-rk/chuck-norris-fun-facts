package ru.heatalways.chucknorrisfunfacts.presentation.screen.main

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.emptyFlow
import ru.heatalways.chucknorrisfunfacts.common.presentation.factories.GenericSavedStateViewModelFactory
import ru.heatalways.chucknorrisfunfacts.core.presentation.base.MviFragment
import ru.heatalways.chucknorrisfunfacts.di.AppComponent
import ru.heatalways.navigation.R
import ru.heatalways.navigation.databinding.FragmentMainBinding
import ru.heatalways.navigation.navigation.extensions.setupWithNavController
import javax.inject.Inject

class MainFragment: MviFragment<
        MainAction,
        MainViewState
        >(R.layout.fragment_main) {

    @Inject
    lateinit var factory: MainViewModel.Factory

    override val viewModel: MainViewModel by viewModels {
        GenericSavedStateViewModelFactory(factory, this)
    }

    private val binding by viewBinding(FragmentMainBinding::bind)

    override val actions get() = emptyFlow<MainAction>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AppComponent.get().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null)
            setupBottomNavigationBar()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        val navGraphIds = listOf(
            R.navigation.nav_graph_search,
            R.navigation.nav_graph_random,
            R.navigation.nav_graph_settings
        )

        binding.bottomNavigationBar.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = childFragmentManager,
            containerId = R.id.mainFragmentContainer,
            intent = requireActivity().intent
        )
    }

    override fun renderState(state: MainViewState) = Unit
}