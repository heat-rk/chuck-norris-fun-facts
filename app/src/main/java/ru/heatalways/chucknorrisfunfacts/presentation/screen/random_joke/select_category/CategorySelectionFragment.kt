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
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.databinding.FragmentSelectCategoryBinding
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.Category
import ru.heatalways.chucknorrisfunfacts.extensions.*
import ru.heatalways.chucknorrisfunfacts.presentation.adapters.CategoriesAdapter
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseMviFragment
import ru.heatalways.chucknorrisfunfacts.presentation.util.TrackedReference
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

        categoriesAdapter.onCategoryClick = { category ->
            action(CategorySelectionAction.OnCategorySelect(category))
        }

        binding.apply {
            categoriesRecyclerView.layoutManager = LinearLayoutManager(context)
            categoriesRecyclerView.adapter = categoriesAdapter

            searchView.onSearchExecute = { query ->
                action(CategorySelectionAction.OnSearchExecute(query))
                searchView.clearFocus()
                hideKeyboard()
            }
        }
    }

    override fun renderState(state: CategorySelectionState) {
        binding.searchView.setSearchButtonVisibility(!state.isCategoriesLoading)

        categoriesAdapter.submitList(state.categories)

        binding.categoriesRecyclerView.isVisible =
            !state.isCategoriesLoading && state.categoriesMessage == null

        setProgressBarVisibility(state.isCategoriesLoading)

        setErrorVisibility(state.categoriesMessage != null, state.categoriesMessage)

        if (state.isScrollingUp)
            binding.categoriesRecyclerView.postScrollToPosition(0)
    }

    override fun onDestroyView() {
        binding.categoriesRecyclerView.adapter = null
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