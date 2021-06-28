package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.terrakok.cicerone.androidx.FragmentScreen
import dagger.hilt.android.AndroidEntryPoint
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.databinding.FragmentSelectCategoryBinding
import ru.heatalways.chucknorrisfunfacts.presentation.adapters.CategoriesAdapter
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseFragment

@AndroidEntryPoint
class SelectCategoryFragment: BaseFragment<FragmentSelectCategoryBinding>() {
    private val selectCategoryViewModel:
            SelectCategoryViewModel by viewModels()

    private val sharedCategorySelectionViewModel:
            SharedCategorySelectionViewModel by activityViewModels()

    private val categoriesAdapter = CategoriesAdapter()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSelectCategoryBinding
        get() = FragmentSelectCategoryBinding::inflate

    override val contentId = R.id.categoriesRecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(R.string.select_category_screen_title)

        categoriesAdapter.onCategoryClick = { category ->
            sharedCategorySelectionViewModel.selectCategory(category)
            selectCategoryViewModel.backToRandomJokeScreen()
        }

        binding.apply {
            categoriesRecyclerView.layoutManager = LinearLayoutManager(context)
            categoriesRecyclerView.adapter = categoriesAdapter

            searchView.onSearchExecute = { query ->
                selectCategoryViewModel.searchCategories(query)
            }
        }

        initCategoriesObserver()
    }

    private fun initCategoriesObserver() {
        observe(selectCategoryViewModel.state) { state ->
            when (state) {
                is SelectCategoryState.Error -> {
                    setProgressBarVisibility(false)
                    setErrorVisibility(true, state.message)
                }
                is SelectCategoryState.Empty -> {
                    setProgressBarVisibility(false)
                    setErrorVisibility(true, getString(R.string.error_not_found))
                }
                is SelectCategoryState.Loading -> {
                    setProgressBarVisibility(true)
                    setErrorVisibility(false)
                }
                is SelectCategoryState.Loaded -> {
                    setProgressBarVisibility(false)
                    setErrorVisibility(false)
                    categoriesAdapter.newList(state.categories)
                }
            }
        }
    }

    companion object {
        fun getScreen() = FragmentScreen { SelectCategoryFragment() }
    }
}