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
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.domain.utils.SnackbarState
import ru.heatalways.chucknorrisfunfacts.domain.utils.StringResource
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
        binding.historyRecyclerView.isVisible =
            !state.isLoading && state.message == null

        renderList(state.jokes)
        renderJokeLoading(state.isJokeLoading)
        renderSelectedCategory(state.category)
        renderLoading(state.isLoading)
        renderError(state.message)

        // single live events
        renderToast(state.toastState)
        renderSnackbar(state.snackbarState)
        renderScrolling(state.isScrollingUp)
    }

    private fun renderLoading(isLoading: Boolean) {
        if (previousState?.isLoading != isLoading)
            setProgressBarVisibility(isLoading)
    }

    private fun renderError(message: StringResource?) {
        if (previousState?.message != message)
            setErrorVisibility(message != null, message)
    }

    private fun renderList(jokes: List<ChuckJoke>) {
        if (previousState?.jokes != jokes)
            adapter.submitList(jokes)
    }

    private fun renderJokeLoading(isVisible: Boolean) {
        if (previousState?.isJokeLoading != isVisible) {
            binding.buttonProgressBar.isVisible = isVisible
            binding.getJokeButton.isVisible = isVisible.not()
        }
    }

    private fun renderSelectedCategory(category: Category) {
        if (previousState?.category != category)
            binding.selectCategoryButton.text = when(category) {
                is Category.Any -> getString(R.string.random_joke_any_category)
                is Category.Specific -> category.name
            }
    }

    private fun renderToast(toastState: ToastState) {
        if (previousState?.toastState != toastState)
            if (toastState is ToastState.Shown)
                showToast(toastState.message)
    }

    private fun renderSnackbar(snackbarState: SnackbarState) {
        if (previousState?.snackbarState != snackbarState)
            if (snackbarState is SnackbarState.Shown)
                showSnackbar(
                    view = binding.root,
                    message = snackbarState.message,
                    buttonText = snackbarState.buttonText,
                    buttonCallback = snackbarState.buttonCallback
                )
            else
                hideSnackbar()
    }

    private fun renderScrolling(isScrollingUp: Boolean) {
        if (previousState?.isScrollingUp != isScrollingUp)
            if (isScrollingUp) {
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