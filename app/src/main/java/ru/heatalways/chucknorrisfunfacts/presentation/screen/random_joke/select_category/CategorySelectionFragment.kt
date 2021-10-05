package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.terrakok.cicerone.androidx.FragmentScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.databinding.FragmentCategorySelectionBinding
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.Category
import ru.heatalways.chucknorrisfunfacts.domain.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.extensions.getSafeTrackedArgument
import ru.heatalways.chucknorrisfunfacts.extensions.hideKeyboard
import ru.heatalways.chucknorrisfunfacts.extensions.postScrollToPosition
import ru.heatalways.chucknorrisfunfacts.extensions.putTrackedReference
import ru.heatalways.chucknorrisfunfacts.presentation.adapters.CategoriesAdapter
import ru.heatalways.chucknorrisfunfacts.presentation.adapters.decorators.MarginItemDecoration
import ru.heatalways.chucknorrisfunfacts.presentation.base.BindingMviFragment
import ru.heatalways.chucknorrisfunfacts.presentation.util.DefaultAppbar
import ru.heatalways.chucknorrisfunfacts.presentation.util.ScrollState
import javax.inject.Inject

@AndroidEntryPoint
class CategorySelectionFragment: BindingMviFragment<
        FragmentCategorySelectionBinding,
        CategorySelectionAction,
        CategorySelectionViewState
>(FragmentCategorySelectionBinding::inflate) {

    @Inject
    lateinit var assistedFactory: CategorySelectionViewModel.Factory

    override val viewModel: CategorySelectionViewModel by viewModels {
        CategorySelectionViewModel.provideFactory(
            assistedFactory = assistedFactory,
            owner = this,
            defaultArgs = arguments,
            onSelect = getSafeTrackedArgument(ON_SELECT_EXTRA) ?: {}
        )
    }

    private val appbar = DefaultAppbar(this)
    private val categoriesAdapter = CategoriesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(appbar) {
            inflate(requireContext(), requireActivity(), rootBinding.topLayout)
            setTitle(R.string.select_category_screen_title)
            initBackButton()
        }

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

            binding.searchView.searches()
                .map { query ->
                    binding.searchView.clearFocus()
                    hideKeyboard()
                    CategorySelectionAction.OnSearchExecute(query)
                }
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
            binding.searchView.setSearchButtonVisibility(!isLoading)
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

    companion object {
        private const val ON_SELECT_EXTRA = "category_selection_fragment.on_select"

        fun getScreen(onSelect: (Category) -> Unit) =
            FragmentScreen {
                CategorySelectionFragment().apply {
                    putTrackedReference(ON_SELECT_EXTRA, onSelect)
                }
            }
    }
}