package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.databinding.FragmentSelectCategoryBinding
import ru.heatalways.chucknorrisfunfacts.extensions.scrollUp
import ru.heatalways.chucknorrisfunfacts.presentation.adapters.CategoriesAdapter
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseFragment
import ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.RandomJokeViewModel

class SelectCategoryFragment: BaseFragment<FragmentSelectCategoryBinding>() {
    private val selectCategoryViewModel: SelectCategoryViewModel by viewModels()
    private val randomJokeViewModel: RandomJokeViewModel by activityViewModels()

    private val categoriesAdapter = CategoriesAdapter()

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSelectCategoryBinding {
        return FragmentSelectCategoryBinding.inflate(inflater, container, false)
    }

    override val contentId = R.id.categoriesRecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(R.string.select_category_screen_title)

        categoriesAdapter.onCategoryClick = { category ->
            randomJokeViewModel.selectCategory(category)
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
                is SelectCategoryState.CategoriesLoadError -> {
                    setProgressBarVisibility(false)
                    setErrorVisibility(true, state.message)
                }
                SelectCategoryState.CategoriesLoading -> {
                    setProgressBarVisibility(true)
                    setErrorVisibility(false)
                }
                is SelectCategoryState.CategoriesLoaded -> {
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