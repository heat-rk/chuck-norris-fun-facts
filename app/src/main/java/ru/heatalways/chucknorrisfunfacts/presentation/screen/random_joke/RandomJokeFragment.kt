package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import dagger.hilt.android.AndroidEntryPoint
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.data.entities.Category
import ru.heatalways.chucknorrisfunfacts.data.entities.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.databinding.FragmentRandomJokeBinding
import ru.heatalways.chucknorrisfunfacts.presentation.adapters.JokesAdapter
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseMviFragment
import ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category.CategorySelectionFragment
import ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category.SharedCategorySelectionViewModel
import javax.inject.Inject

@AndroidEntryPoint
class RandomJokeFragment: BaseMviFragment<
        FragmentRandomJokeBinding,
        RandomJokeContract.Action,
        RandomJokeContract.State,
        RandomJokeContract.Effect
>() {
    override val viewModel: RandomJokeViewModel by viewModels()

    private val adapter = JokesAdapter()

    private val sharedCategorySelectionViewModel:
            SharedCategorySelectionViewModel by activityViewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRandomJokeBinding
        get() = FragmentRandomJokeBinding::inflate

    override val contentId = R.id.historyRecyclerView

    @Inject
    lateinit var router: Router

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(R.string.random_joke_screen_title)

        binding.apply {
            getJokeButton.setOnClickListener {
                sharedCategorySelectionViewModel.selectedCategory.value?.let { category ->
                    action(RandomJokeContract.Action.OnRandomJokeRequest(category))
                }
            }

            selectCategoryButton.setOnClickListener {
                action(RandomJokeContract.Action.OnCategorySelectionButtonClick)
            }

            historyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            historyRecyclerView.adapter = adapter
        }


        initCategorySelectionObserver()
    }

    override fun renderState(state: RandomJokeContract.State) {
        when (state) {
            is RandomJokeContract.State.Loading -> loadingViewState()
            is RandomJokeContract.State.JokeLoading -> jokeLoadingViewState()
            is RandomJokeContract.State.Empty -> messageViewState(getString(R.string.random_joke_empty_history))
            is RandomJokeContract.State.Loaded -> loadedViewState(state.jokes)
        }
    }

    override fun handleEffect(effect: RandomJokeContract.Effect) {
        when (effect) {
            is RandomJokeContract.Effect.NavigateToCategorySelectionScreen -> {
                router.navigateTo(CategorySelectionFragment.getScreen())
            }
            is RandomJokeContract.Effect.Error -> {
                showMessage(effect.message)
            }
        }
    }

    private fun initCategorySelectionObserver() {
        observe(sharedCategorySelectionViewModel.selectedCategory) { category ->
            binding.selectCategoryButton.text = when(category) {
                is Category.Any -> getString(R.string.random_joke_any_category)
                is Category.Specific -> category.name
            }
        }
    }

    private fun messageViewState(message: String?) {
        binding.buttonProgressBar.isVisible = false
        binding.getJokeButton.isVisible = true
        setErrorVisibility(true, message)
        setProgressBarVisibility(false)
    }

    private fun loadingViewState() {
        binding.buttonProgressBar.isVisible = false
        binding.getJokeButton.isVisible = true
        setErrorVisibility(false)
        setProgressBarVisibility(true)
    }

    private fun jokeLoadingViewState() {
        binding.buttonProgressBar.isVisible = true
        binding.getJokeButton.isVisible = false
        setErrorVisibility(false)
        setProgressBarVisibility(false)
    }

    private fun loadedViewState(jokes: List<ChuckJoke>) {
        binding.buttonProgressBar.isVisible = false
        binding.getJokeButton.isVisible = true

        setErrorVisibility(false)
        setProgressBarVisibility(false)
        adapter.submitList(jokes) {
            binding.historyRecyclerView.scrollToPosition(0)
        }
    }

    companion object {
        fun getScreen() = FragmentScreen { RandomJokeFragment() }
    }
}