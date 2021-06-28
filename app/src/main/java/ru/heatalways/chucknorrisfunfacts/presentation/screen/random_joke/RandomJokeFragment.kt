package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.github.terrakok.cicerone.androidx.FragmentScreen
import dagger.hilt.android.AndroidEntryPoint
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.data.entities.Category
import ru.heatalways.chucknorrisfunfacts.databinding.FragmentRandomJokeBinding
import ru.heatalways.chucknorrisfunfacts.extensions.setVisibleOrGone
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseFragment
import ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category.SharedCategorySelectionViewModel

@AndroidEntryPoint
class RandomJokeFragment: BaseFragment<FragmentRandomJokeBinding>() {
    private val randomJokeViewModel: RandomJokeViewModel by viewModels()

    private val sharedCategorySelectionViewModel:
            SharedCategorySelectionViewModel by activityViewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRandomJokeBinding
        get() = FragmentRandomJokeBinding::inflate

    override val contentId = R.id.historyRecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(R.string.random_joke_screen_title)

        binding.getJokeButton.setOnClickListener {
            sharedCategorySelectionViewModel.selectedCategory.value?.let { category ->
                randomJokeViewModel.fetchRandomJoke(category)
            }
        }

        binding.selectCategoryButton.setOnClickListener {
            randomJokeViewModel.navigateToCategorySelectionScreen()
        }

        initRandomJokeObserver()
        initCategorySelectionObserver()
    }

    private fun initRandomJokeObserver() {
        observe(randomJokeViewModel.state) { state ->
            when (state) {
                is RandomJokeState.Loading -> {
                    binding.buttonProgressBar.setVisibleOrGone(true)
                    binding.getJokeButton.setVisibleOrGone(false)
                }
                is RandomJokeState.Error -> {
                    binding.buttonProgressBar.setVisibleOrGone(false)
                    binding.getJokeButton.setVisibleOrGone(true)
                    showMessage(state.message)
                }
                is RandomJokeState.Loaded -> {
                    binding.buttonProgressBar.setVisibleOrGone(false)
                    binding.getJokeButton.setVisibleOrGone(true)
                }
            }
        }
    }

    private fun initCategorySelectionObserver() {
        observe(sharedCategorySelectionViewModel.selectedCategory) { category ->
            binding.selectCategoryButton.text = when(category) {
                Category.Any -> getString(R.string.random_joke_any_category)
                is Category.Specific -> category.name
            }
        }
    }

    companion object {
        fun getScreen() = FragmentScreen { RandomJokeFragment() }
    }
}