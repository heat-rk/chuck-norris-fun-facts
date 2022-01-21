package ru.heatalways.chucknorrisfunfacts.presentation.screen.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.emptyFlow
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.databinding.FragmentMainBinding
import ru.heatalways.chucknorrisfunfacts.extensions.setupWithNavController
import ru.heatalways.chucknorrisfunfacts.core.base.MviFragment

class MainFragment: MviFragment<
        MainAction,
        MainViewState
>(R.layout.fragment_main) {

    override val viewModel: MainViewModel by viewModels()
    private val binding by viewBinding(FragmentMainBinding::bind)

    override val actions get() = emptyFlow<MainAction>()

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