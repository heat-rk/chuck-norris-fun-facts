package ru.heatalways.chucknorrisfunfacts.search_joke.presentation.screen.search_joke

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.*
import ru.heatalways.chucknorrisfunfacts.common.extensions.postScrollToPosition
import ru.heatalways.chucknorrisfunfacts.common.general.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.common.presentation.factories.GenericSavedStateViewModelFactory
import ru.heatalways.chucknorrisfunfacts.common.presentation.utils.ScrollState
import ru.heatalways.chucknorrisfunfacts.core.databinding.FragmentSearchJokeBinding
import ru.heatalways.chucknorrisfunfacts.core.domain.models.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.core.presentation.adapters.JokesAdapter
import ru.heatalways.chucknorrisfunfacts.core.presentation.adapters.decorators.MarginItemDecoration
import ru.heatalways.chucknorrisfunfacts.core.presentation.base.MviFragment
import ru.heatalways.chucknorrisfunfacts.core.presentation.custom_view.search_query_view.SearchQueryView
import ru.heatalways.chucknorrisfunfacts.search_joke.R
import ru.heatalways.chucknorrisfunfacts.search_joke.di.SearchJokeFeatureComponentHolder
import javax.inject.Inject

internal class SearchJokeFragment: MviFragment<
        SearchJokeAction,
        SearchJokeViewState
        >(R.layout.fragment_search_joke) {

    @Inject
    lateinit var viewModelFactory: SearchJokeViewModel.Factory

    override val viewModel: SearchJokeViewModel by viewModels {
        GenericSavedStateViewModelFactory(viewModelFactory, this)
    }

    private val binding by viewBinding(FragmentSearchJokeBinding::bind)
    private val jokesAdapter = JokesAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        SearchJokeFeatureComponentHolder.getComponent().inject(this)
    }

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