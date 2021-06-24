package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.github.terrakok.cicerone.androidx.FragmentScreen
import dagger.hilt.android.AndroidEntryPoint
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.data.entities.Category
import ru.heatalways.chucknorrisfunfacts.databinding.FragmentRandomJokeBinding
import ru.heatalways.chucknorrisfunfacts.extensions.setVisibleOrGone
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseFragment

@AndroidEntryPoint
class RandomJokeFragment: BaseFragment<FragmentRandomJokeBinding>() {
    private val randomJokeViewModel: RandomJokeViewModel by activityViewModels()

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRandomJokeBinding {
        return FragmentRandomJokeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(R.string.random_joke_screen_title)

        binding.getJokeButton.setOnClickListener {
            randomJokeViewModel.fetchRandomJoke()
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
                is RandomJokeState.JokeLoading -> {
                    binding.buttonProgressBar.setVisibleOrGone(true)
                    binding.getJokeButton.setVisibleOrGone(false)
                }
                is RandomJokeState.JokeLoadError -> {
                    binding.buttonProgressBar.setVisibleOrGone(false)
                    binding.getJokeButton.setVisibleOrGone(true)
                    showMessage(state.message)
                }
                is RandomJokeState.JokesLoaded -> {
                    binding.buttonProgressBar.setVisibleOrGone(false)
                    binding.getJokeButton.setVisibleOrGone(true)
                }
            }
        }
    }

    private fun initCategorySelectionObserver() {
        observe(randomJokeViewModel.selectedCategory) { category ->
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