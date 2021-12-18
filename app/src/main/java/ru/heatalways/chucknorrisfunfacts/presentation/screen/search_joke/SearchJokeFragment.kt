package ru.heatalways.chucknorrisfunfacts.presentation.screen.search_joke

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.core.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.databinding.FragmentSearchJokeBinding
import ru.heatalways.chucknorrisfunfacts.domain.models.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.extensions.postScrollToPosition
import ru.heatalways.chucknorrisfunfacts.presentation.adapters.JokesAdapter
import ru.heatalways.chucknorrisfunfacts.presentation.adapters.decorators.MarginItemDecoration
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviFragment
import ru.heatalways.chucknorrisfunfacts.presentation.custom_view.search_query_view.SearchQueryView
import ru.heatalways.chucknorrisfunfacts.presentation.util.ScrollState

@AndroidEntryPoint
class SearchJokeFragment: MviFragment<
        SearchJokeAction,
        SearchJokeViewState
>(R.layout.fragment_search_joke) {

    override val viewModel: SearchJokeViewModel by viewModels()
    private val binding by viewBinding(FragmentSearchJokeBinding::bind)
    private val jokesAdapter = JokesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        binding.appbar.toolbar.setTitle(R.string.search_joke_screen_title)

        with(binding.jokesRecyclerView) {
            addItemDecoration(MarginItemDecoration(R.dimen.paddingMD))
            layoutManager = LinearLayoutManager(context)
            adapter = jokesAdapter
        }
    }

    override val actions get() =
        merge(
            binding.searchView.queryChanges()
                .distinctUntilChanged()
                .debounce(SearchQueryView.DEFAULT_DEBOUNCE_TIME)
                .filter { binding.searchView.isQueryLengthValid }
                .map { SearchJokeAction.OnSearchExecute(it) }
        )

    override fun renderState(state: SearchJokeViewState) {
        binding.jokesRecyclerView.isVisible =
            !state.isJokesLoading && state.jokesMessage == null

        renderList(state.jokes)
        renderLoading(state.isJokesLoading)
        renderError(state.jokesMessage)
        renderScrolling(state.scrollState)
    }

    private fun renderLoading(isLoading: Boolean) {
        if (previousState?.isJokesLoading != isLoading)
            binding.shimmerLayout.root.isVisible = isLoading
    }

    private fun renderError(message: StringResource?) {
        if (previousState?.jokesMessage != message)
            if (message != null) binding.errorView.show(message)
            else binding.errorView.hide()
    }

    private fun renderList(jokes: List<ChuckJoke>) {
        if (previousState?.jokes != jokes)
            jokesAdapter.submitList(jokes)
    }

    private fun renderScrolling(scrollState: ScrollState) {
        if (previousState?.scrollState != scrollState)
            if (scrollState is ScrollState.ScrollingUp) {
                binding.jokesRecyclerView.postScrollToPosition(0)
            }
    }

    override fun onResume() {
        super.onResume()
        binding.shimmerLayout.root.startShimmer()
    }

    override fun onPause() {
        binding.shimmerLayout.root.stopShimmer()
        super.onPause()
    }

    override fun onDestroyView() {
        with(binding) {
            searchView.clearListeners()
            jokesRecyclerView.adapter = null
        }

        super.onDestroyView()
    }
}