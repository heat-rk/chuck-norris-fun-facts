package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import dagger.hilt.android.AndroidEntryPoint
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.databinding.FragmentSelectCategoryBinding
import ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.select_category.CategorySelectionAction
import ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.select_category.CategorySelectionEffect
import ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.select_category.CategorySelectionState
import ru.heatalways.chucknorrisfunfacts.presentation.adapters.CategoriesAdapter
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseMviFragment
import javax.inject.Inject

@AndroidEntryPoint
class CategorySelectionFragment: BaseMviFragment<
        FragmentSelectCategoryBinding,
        CategorySelectionAction,
        CategorySelectionState,
        CategorySelectionEffect
>() {
    override val viewModel: CategorySelectionViewModel by viewModels()

    private val categoriesAdapter = CategoriesAdapter()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSelectCategoryBinding
        get() = FragmentSelectCategoryBinding::inflate

    @Inject
    lateinit var router: Router

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(R.string.select_category_screen_title)

        categoriesAdapter.onCategoryClick = { category ->
            action(CategorySelectionAction.OnCategorySelect(category))
        }

        binding.apply {
            categoriesRecyclerView.layoutManager = LinearLayoutManager(context)
            categoriesRecyclerView.adapter = categoriesAdapter

            searchView.onSearchExecute = { query ->
                action(CategorySelectionAction.OnSearchExecute(query))
            }
        }
    }

    override fun renderState(state: CategorySelectionState) {
        binding.categoriesRecyclerView.isVisible =
            !state.isLoading && state.message == null

        categoriesAdapter.submitList(state.categories) {
            binding.categoriesRecyclerView.scrollToPosition(0)
        }

        setProgressBarVisibility(state.isLoading)

        setErrorVisibility(state.message != null, state.message)
    }

    override fun handleEffect(effect: CategorySelectionEffect) {
        when (effect) {
            CategorySelectionEffect.GoBack -> {
                router.exit()
            }
        }
    }

    companion object {
        fun getScreen() = FragmentScreen { CategorySelectionFragment() }
    }
}