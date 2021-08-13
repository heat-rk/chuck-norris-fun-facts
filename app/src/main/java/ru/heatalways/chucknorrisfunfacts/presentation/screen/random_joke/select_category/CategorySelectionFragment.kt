package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import dagger.hilt.android.AndroidEntryPoint
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.databinding.FragmentSelectCategoryBinding
import ru.heatalways.chucknorrisfunfacts.extensions.toTextResource
import ru.heatalways.chucknorrisfunfacts.presentation.adapters.CategoriesAdapter
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseMviFragment
import javax.inject.Inject

@AndroidEntryPoint
class CategorySelectionFragment: BaseMviFragment<
        FragmentSelectCategoryBinding,
        CategorySelectionContract.Action,
        CategorySelectionContract.State,
        CategorySelectionContract.Effect
>() {
    override val viewModel: CategorySelectionViewModel by viewModels()

    private val sharedCategorySelectionViewModel:
            SharedCategorySelectionViewModel by activityViewModels()

    private val categoriesAdapter = CategoriesAdapter()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSelectCategoryBinding
        get() = FragmentSelectCategoryBinding::inflate

    override val contentId = R.id.categoriesRecyclerView

    @Inject
    lateinit var router: Router

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(R.string.select_category_screen_title)

        categoriesAdapter.onCategoryClick = { category ->
            sharedCategorySelectionViewModel.selectCategory(category)
            action(CategorySelectionContract.Action.OnCategoryClick)
        }

        binding.apply {
            categoriesRecyclerView.layoutManager = LinearLayoutManager(context)
            categoriesRecyclerView.adapter = categoriesAdapter

            searchView.onSearchExecute = { query ->
                action(CategorySelectionContract.Action.OnSearchExecute(query))
            }
        }
    }

    override fun renderState(state: CategorySelectionContract.State) {
        categoriesAdapter.submitList(state.categories) {
            binding.categoriesRecyclerView.scrollToPosition(0)
        }

        setProgressBarVisibility(state.isLoading)

        setErrorVisibility(state.message != null, state.message)
    }

    override fun handleEffect(effect: CategorySelectionContract.Effect) {
        when (effect) {
            CategorySelectionContract.Effect.GoBack -> {
                router.exit()
            }
        }
    }

    companion object {
        fun getScreen() = FragmentScreen { CategorySelectionFragment() }
    }
}