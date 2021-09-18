package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.terrakok.cicerone.androidx.FragmentScreen
import dagger.hilt.android.AndroidEntryPoint
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.databinding.FragmentRandomJokeBinding
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.Category
import ru.heatalways.chucknorrisfunfacts.domain.utils.SnackbarState
import ru.heatalways.chucknorrisfunfacts.domain.utils.ToastState
import ru.heatalways.chucknorrisfunfacts.extensions.onScrolledToLastItem
import ru.heatalways.chucknorrisfunfacts.extensions.postScrollToPosition
import ru.heatalways.chucknorrisfunfacts.extensions.showToast
import ru.heatalways.chucknorrisfunfacts.presentation.adapters.JokesAdapter
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseMviFragment

@AndroidEntryPoint
class RandomJokeFragment: BaseMviFragment<
        FragmentRandomJokeBinding,
        RandomJokeAction,
        RandomJokeViewState
>() {
    override val viewModel: RandomJokeViewModel by viewModels()

    private val adapter = JokesAdapter()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRandomJokeBinding
        get() = FragmentRandomJokeBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(R.string.random_joke_screen_title)
        initMenu(R.menu.random_jokes_history_menu)

        binding.apply {
            getJokeButton.setOnClickListener {
                action(RandomJokeAction.OnRandomJokeRequest)
            }

            selectCategoryButton.setOnClickListener {
                action(RandomJokeAction.OnCategorySelectionButtonClick)
            }

            historyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            historyRecyclerView.adapter = adapter
            historyRecyclerView.onScrolledToLastItem {
                action(RandomJokeAction.OnNextPage)
            }
        }
    }

    override fun renderState(state: RandomJokeViewState) {
        adapter.submitList(state.jokes)

        binding.historyRecyclerView.isVisible =
            !state.isLoading && state.message == null

        binding.buttonProgressBar.isVisible = state.isJokeLoading
        binding.getJokeButton.isVisible = state.isJokeLoading.not()

        binding.selectCategoryButton.text = when(state.category) {
            is Category.Any -> getString(R.string.random_joke_any_category)
            is Category.Specific -> state.category.name
        }

        setProgressBarVisibility(state.isLoading)

        setErrorVisibility(state.message != null, state.message)

        // single live events
        if (state.toastState is ToastState.Shown)
            showToast(state.toastState.message)

        if (state.snackbarState is SnackbarState.Shown)
            showSnackbar(
                view = binding.root,
                message = state.snackbarState.message,
                buttonText = state.snackbarState.buttonText,
                buttonCallback = state.snackbarState.buttonCallback
            )
        else hideSnackbar()

        if (state.isScrollingUp) {
            binding.root.transitionToStart()
            binding.historyRecyclerView.postScrollToPosition(0)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.removeAll -> action(RandomJokeAction.RemoveAll)
        }

        return true
    }

    override fun onDestroyView() {
        binding.historyRecyclerView.clearOnScrollListeners()
        binding.historyRecyclerView.adapter = null
        super.onDestroyView()
    }

    companion object {
        fun getScreen() = FragmentScreen { RandomJokeFragment() }
    }
}