package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.databinding.FragmentCategorySelectionBinding
import ru.heatalways.chucknorrisfunfacts.domain.models.Category
import ru.heatalways.chucknorrisfunfacts.core.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.extensions.initBackButton
import ru.heatalways.chucknorrisfunfacts.extensions.postScrollToPosition
import ru.heatalways.chucknorrisfunfacts.presentation.adapters.CategoriesAdapter
import ru.heatalways.chucknorrisfunfacts.presentation.adapters.decorators.MarginItemDecoration
import ru.heatalways.chucknorrisfunfacts.presentation.base.BindingMviFragment
import ru.heatalways.chucknorrisfunfacts.presentation.custom_view.search_query_view.SearchQueryView
import ru.heatalways.chucknorrisfunfacts.presentation.util.ScrollState
import ru.heatalways.chucknorrisfunfacts.presentation.util.appbars.DefaultAppbar

@AndroidEntryPoint
class CategorySelectionFragment: BindingMviFragment<
        FragmentCategorySelectionBinding,
        CategorySelectionAction,
        CategorySelectionViewState
>(FragmentCategorySelectionBinding::inflate) {

    override val viewModel: CategorySelectionViewModel by viewModels()

    override val appbar get() = DefaultAppbar(
        parentLayoutId = R.id.topLayout,
        builder = {
            setTitle(R.string.select_category_screen_title)
            initBackButton(requireActivity())
        }
    )

    private val categoriesAdapter = CategoriesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            binding.shimmerLayout.isVisible = isLoading
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
        binding.shimmerLayout.startShimmer()
    }

    override fun onPause() {
        binding.shimmerLayout.stopShimmer()
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