package ru.heatalways.chucknorrisfunfacts.presentation.screen.search_joke

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.terrakok.cicerone.androidx.FragmentScreen
import dagger.hilt.android.AndroidEntryPoint
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.databinding.FragmentSearchJokeBinding
import ru.heatalways.chucknorrisfunfacts.domain.interactors.search_joke.SearchJokeAction
import ru.heatalways.chucknorrisfunfacts.domain.interactors.search_joke.SearchJokeViewEffect
import ru.heatalways.chucknorrisfunfacts.domain.interactors.search_joke.SearchJokeViewState
import ru.heatalways.chucknorrisfunfacts.extensions.hideKeyboard
import ru.heatalways.chucknorrisfunfacts.extensions.postScrollToPosition
import ru.heatalways.chucknorrisfunfacts.presentation.adapters.JokesAdapter
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseMviFragment

@AndroidEntryPoint
class SearchJokeFragment: BaseMviFragment<
        FragmentSearchJokeBinding,
        SearchJokeAction,
        SearchJokeViewState,
        SearchJokeViewEffect
>() {
    override val viewModel: SearchJokeViewModel by viewModels()

    private val jokesAdapter = JokesAdapter()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchJokeBinding
        get() = FragmentSearchJokeBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(R.string.search_joke_screen_title)

        binding.apply {
            jokesRecyclerView.layoutManager = LinearLayoutManager(context)
            jokesRecyclerView.adapter = jokesAdapter

            searchView.onSearchExecute = { searchQuery ->
                action(SearchJokeAction.OnSearchExecute(searchQuery))
                searchView.clearFocus()
                hideKeyboard()
            }
        }
    }

    override fun renderState(state: SearchJokeViewState) {
        binding.jokesRecyclerView.isVisible =
            !state.isLoading && state.message == null

        jokesAdapter.submitList(state.jokes)

        setProgressBarVisibility(state.isLoading)

        setErrorVisibility(state.message != null, state.message)
    }

    override fun handleEffect(effect: SearchJokeViewEffect) {
        when (effect) {
            SearchJokeViewEffect.ScrollUp ->
                binding.jokesRecyclerView.postScrollToPosition(0)
        }
    }

    companion object {
        fun getScreen() = FragmentScreen { SearchJokeFragment() }
    }
}