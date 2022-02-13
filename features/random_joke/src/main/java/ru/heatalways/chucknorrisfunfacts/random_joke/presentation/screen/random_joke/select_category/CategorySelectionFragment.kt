package ru.heatalways.chucknorrisfunfacts.random_joke.presentation.screen.random_joke.select_category

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.*
import ru.heatalways.chucknorrisfunfacts.common.extensions.initBackButton
import ru.heatalways.chucknorrisfunfacts.common.extensions.postScrollToPosition
import ru.heatalways.chucknorrisfunfacts.common.general.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.common.presentation.factories.GenericSavedStateViewModelFactory
import ru.heatalways.chucknorrisfunfacts.common.presentation.utils.ScrollState
import ru.heatalways.chucknorrisfunfacts.core.databinding.FragmentCategorySelectionBinding
import ru.heatalways.chucknorrisfunfacts.core.domain.models.Category
import ru.heatalways.chucknorrisfunfacts.core.presentation.adapters.CategoriesAdapter
import ru.heatalways.chucknorrisfunfacts.core.presentation.adapters.decorators.MarginItemDecoration
import ru.heatalways.chucknorrisfunfacts.core.presentation.base.MviFragment
import ru.heatalways.chucknorrisfunfacts.core.presentation.custom_view.search_query_view.SearchQueryView
import ru.heatalways.chucknorrisfunfacts.random_joke.R
import ru.heatalways.chucknorrisfunfacts.random_joke.di.RandomJokeFeatureComponentHolder
import javax.inject.Inject

internal class CategorySelectionFragment: MviFragment<
        CategorySelectionAction,
        CategorySelectionViewState
        >(R.layout.fragment_category_selection) {

    @Inject
    lateinit var viewModelFactory: CategorySelectionViewModel.Factory

    override val viewModel: CategorySelectionViewModel by viewModels {
        GenericSavedStateViewModelFactory(viewModelFactory, this)
    }

    private val binding by viewBinding(FragmentCategorySelectionBinding::bind)

    private val categoriesAdapter = CategoriesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RandomJokeFeatureComponentHolder.getComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.appbar.toolbar.setTitle(R.string.select_category_screen_title)
        binding.appbar.toolbar.initBackButton(
            activity = requireActivity(),
            navigationIcon = R.drawable.ic_back_arrow,
            navigationContentDescription = R.string.navigation_back
        )

        with(binding.categoriesRecyclerView) {
            addItemDecoration(MarginItemDecoration(R.dimen.paddingMD))
            layoutManager = LinearLayoutManager(context)
            adapter = categoriesAdapter
        }
    }

    override val actions get() =
        merge(
            categoriesAdapter.categoryClicks()
                .map { CategorySelectionAction.OnCategorySelect(it) },

            binding.searchView.queryChanges()
                .distinctUntilChanged()
                .debounce(SearchQueryView.DEFAULT_DEBOUNCE_TIME)
                .filter { binding.searchView.isQueryLengthValid }
                .map { query -> CategorySelectionAction.OnSearchExecute(query) }
        )

    override fun renderState(state: CategorySelectionViewState) {
        binding.categoriesRecyclerView.isVisible =
            !state.isCategoriesLoading && state.categoriesMessage == null

        renderList(state.categories)
        renderLoading(state.isCategoriesLoading)
        renderError(state.categoriesMessage)
        renderScrolling(state.scrollState)
    }

    private fun renderLoading(isLoading: Boolean) {
        if (previousState?.isCategoriesLoading != isLoading) {
            binding.shimmerLayout.root.isVisible = isLoading
        }
    }

    private fun renderError(message: StringResource?) {
        if (previousState?.categoriesMessage != message)
            if (message != null) binding.errorView.show(message)
            else binding.errorView.hide()
    }

    private fun renderList(categories: List<Category>) {
        if (previousState?.categories != categories)
            categoriesAdapter.submitList(categories)
    }

    private fun renderScrolling(scrollState: ScrollState) {
        if (previousState?.scrollState != scrollState)
            if (scrollState == ScrollState.ScrollingUp)
                binding.categoriesRecyclerView.postScrollToPosition(0)
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
            categoriesRecyclerView.clearOnScrollListeners()
            categoriesRecyclerView.adapter = null
        }

        super.onDestroyView()
    }
}