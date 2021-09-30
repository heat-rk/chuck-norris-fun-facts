package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.terrakok.cicerone.androidx.FragmentScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.databinding.FragmentSelectCategoryBinding
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.Category
import ru.heatalways.chucknorrisfunfacts.domain.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.extensions.getSafeTrackedArgument
import ru.heatalways.chucknorrisfunfacts.extensions.hideKeyboard
import ru.heatalways.chucknorrisfunfacts.extensions.postScrollToPosition
import ru.heatalways.chucknorrisfunfacts.extensions.putTrackedReference
import ru.heatalways.chucknorrisfunfacts.presentation.adapters.CategoriesAdapter
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseMviFragment
import javax.inject.Inject

@AndroidEntryPoint
class CategorySelectionFragment: BaseMviFragment<
        FragmentSelectCategoryBinding,
        CategorySelectionAction,
        CategorySelectionState
>() {
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

    private val categoriesAdapter = CategoriesAdapter()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSelectCategoryBinding
        get() = FragmentSelectCategoryBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(R.string.select_category_screen_title)
        initToolbarBackButton()

        with(binding) {
            categoriesRecyclerView.layoutManager = LinearLayoutManager(context)
            categoriesRecyclerView.adapter = categoriesAdapter
        }
    }

    override fun actions() = merge(
        categoriesAdapter.categoryClicks()
            .map { CategorySelectionAction.OnCategorySelect(it) },

        binding.searchView.searches()
            .map { query ->
                binding.searchView.clearFocus()
                hideKeyboard()
                CategorySelectionAction.OnSearchExecute(query)
            }
    )

    override fun renderState(state: CategorySelectionState) {
        binding.categoriesRecyclerView.isVisible =
            !state.isCategoriesLoading && state.categoriesMessage == null

        renderList(state.categories)
        renderLoading(state.isCategoriesLoading)
        renderError(state.categoriesMessage)
        renderScrolling(state.isScrollingUp)
    }

    private fun renderLoading(isLoading: Boolean) {
        if (previousState?.isCategoriesLoading != isLoading) {
            binding.searchView.setSearchButtonVisibility(!isLoading)
            setProgressBarVisibility(isLoading)
        }
    }

    private fun renderError(message: StringResource?) {
        if (previousState?.categoriesMessage != message)
            setErrorVisibility(message != null, message)
    }

    private fun renderList(categories: List<Category>) {
        if (previousState?.categories != categories)
            categoriesAdapter.submitList(categories)
    }

    private fun renderScrolling(isScrollingUp: Boolean) {
        if (previousState?.isScrollingUp != isScrollingUp)
            if (isScrollingUp)
                binding.categoriesRecyclerView.postScrollToPosition(0)
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