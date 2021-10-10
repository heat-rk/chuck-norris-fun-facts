package ru.heatalways.chucknorrisfunfacts.presentation.screen.search_joke

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.databinding.FragmentSearchJokeBinding
import ru.heatalways.chucknorrisfunfacts.domain.models.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.domain.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.extensions.hideKeyboard
import ru.heatalways.chucknorrisfunfacts.extensions.postScrollToPosition
import ru.heatalways.chucknorrisfunfacts.presentation.adapters.JokesAdapter
import ru.heatalways.chucknorrisfunfacts.presentation.adapters.decorators.MarginItemDecoration
import ru.heatalways.chucknorrisfunfacts.presentation.base.BindingMviFragment
import ru.heatalways.chucknorrisfunfacts.presentation.custom_view.SearchQueryView
import ru.heatalways.chucknorrisfunfacts.presentation.util.ScrollState
import ru.heatalways.chucknorrisfunfacts.presentation.util.appbars.DefaultAppbar

@AndroidEntryPoint
class SearchJokeFragment: BindingMviFragment<
        FragmentSearchJokeBinding,
        SearchJokeAction,
        SearchJokeViewState
>(FragmentSearchJokeBinding::inflate) {

    override val viewModel: SearchJokeViewModel by viewModels()
    private val jokesAdapter = JokesAdapter()

    override val appbar get() = DefaultAppbar(
        parentLayoutId = R.id.topLayout,
        builder = {
            setTitle(R.string.search_joke_screen_title)
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            binding.shimmerLayout.isVisible = isLoading
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
                binding.root.transitionToStart()
                binding.jokesRecyclerView.postScrollToPosition(0)
            }
    }

    override fun onResume() {
        super.onResume()
        binding.shimmerLayout.startShimmer()
    }

    override fun onPause() {
        binding.shimmerLayout.stopShimmer()
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