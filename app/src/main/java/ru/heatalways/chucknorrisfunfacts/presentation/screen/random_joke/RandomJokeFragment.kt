package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke

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
import ru.heatalways.chucknorrisfunfacts.data.entities.Category
import ru.heatalways.chucknorrisfunfacts.databinding.FragmentRandomJokeBinding
import ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.RandomJokeAction
import ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.RandomJokeViewEffect
import ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.RandomJokeViewState
import ru.heatalways.chucknorrisfunfacts.presentation.adapters.JokesAdapter
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseMviFragment
import ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category.CategorySelectionFragment
import javax.inject.Inject

@AndroidEntryPoint
class RandomJokeFragment: BaseMviFragment<
        FragmentRandomJokeBinding,
        RandomJokeAction,
        RandomJokeViewState,
        RandomJokeViewEffect
>() {
    override val viewModel: RandomJokeViewModel by viewModels()

    private val adapter = JokesAdapter()

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
                action(RandomJokeAction.OnRandomJokeRequest)
            }

            selectCategoryButton.setOnClickListener {
                action(RandomJokeAction.OnCategorySelectionButtonClick)
            }

            historyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            historyRecyclerView.adapter = adapter
        }
    }

    override fun renderState(state: RandomJokeViewState) {
        setErrorVisibility(state.message != null, state.message)

        setProgressBarVisibility(state.isLoading)

        binding.buttonProgressBar.isVisible = state.isJokeLoading
        binding.getJokeButton.isVisible = state.isJokeLoading.not()

        binding.selectCategoryButton.text = when(state.category) {
            is Category.Any -> getString(R.string.random_joke_any_category)
            is Category.Specific -> state.category.name
        }

        adapter.submitList(state.jokes) {
            binding.historyRecyclerView.scrollToPosition(0)
        }
    }

    override fun handleEffect(effect: RandomJokeViewEffect) {
        when (effect) {
            is RandomJokeViewEffect.NavigateToCategorySelectionScreen -> {
                router.navigateTo(CategorySelectionFragment.getScreen())
            }
            is RandomJokeViewEffect.Error -> {
                showMessage(effect.message)
            }
        }
    }

    companion object {
        fun getScreen() = FragmentScreen { RandomJokeFragment() }
    }
}