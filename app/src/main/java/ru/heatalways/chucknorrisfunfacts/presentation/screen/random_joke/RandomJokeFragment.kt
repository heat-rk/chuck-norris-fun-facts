package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.terrakok.cicerone.androidx.FragmentScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.databinding.FragmentRandomJokeBinding
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.Category
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.presentation.util.SnackbarState
import ru.heatalways.chucknorrisfunfacts.domain.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.presentation.util.ToastState
import ru.heatalways.chucknorrisfunfacts.extensions.postScrollToPosition
import ru.heatalways.chucknorrisfunfacts.extensions.scrollsToLastItem
import ru.heatalways.chucknorrisfunfacts.extensions.showToast
import ru.heatalways.chucknorrisfunfacts.presentation.adapters.JokesAdapter
import ru.heatalways.chucknorrisfunfacts.presentation.adapters.decorators.MarginItemDecoration
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseMviFragment
import ru.heatalways.chucknorrisfunfacts.presentation.util.IndefiniteSnackbar
import ru.heatalways.chucknorrisfunfacts.presentation.util.ScrollState
import ru.ldralighieri.corbind.appcompat.itemClicks
import ru.ldralighieri.corbind.view.clicks

@AndroidEntryPoint
class RandomJokeFragment: BaseMviFragment<
        FragmentRandomJokeBinding,
        RandomJokeAction,
        RandomJokeViewState
>() {
    override val viewModel: RandomJokeViewModel by viewModels()

    private val jokesAdapter = JokesAdapter()
    private val snackbar = IndefiniteSnackbar()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRandomJokeBinding
        get() = FragmentRandomJokeBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(R.string.random_joke_screen_title)
        initMenu(R.menu.random_jokes_history_menu)

        with(binding.historyRecyclerView) {
            addItemDecoration(MarginItemDecoration(R.dimen.paddingMD))
            layoutManager = LinearLayoutManager(requireContext())
            adapter = jokesAdapter
        }
    }

    override val actions get() =
        merge(
            binding.getJokeButton.clicks()
                .map { RandomJokeAction.OnRandomJokeRequest },

            binding.selectCategoryButton.clicks()
                .map { RandomJokeAction.OnCategorySelectionButtonClick },

            binding.historyRecyclerView.scrollsToLastItem()
                .map { RandomJokeAction.OnNextPage },

            toolbar.itemClicks()
                .map { RandomJokeAction.OnMenuItemSelect(it.itemId) }
        )

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
        renderScrolling(state.scrollState)
    }

    private fun renderLoading(isLoading: Boolean) {
        if (previousState?.isLoading != isLoading)
            binding.shimmerLayout.isVisible = isLoading
    }

    private fun renderError(message: StringResource?) {
        if (previousState?.message != message)
            setErrorVisibility(message != null, message)
    }

    private fun renderList(jokes: List<ChuckJoke>) {
        if (previousState?.jokes != jokes)
            jokesAdapter.submitList(jokes)
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
                snackbar.show(
                    view = binding.coordinatorLayout,
                    message = snackbarState.message,
                    buttonText = snackbarState.buttonText,
                    buttonCallback = snackbarState.buttonCallback
                )
            else
                snackbar.hide()
    }

    private fun renderScrolling(scrollState: ScrollState) {
        if (previousState?.scrollState != scrollState)
            if (scrollState == ScrollState.ScrollingUp) {
                binding.motionLayout.transitionToStart()
                binding.historyRecyclerView.postScrollToPosition(0)
            }
    }

    override fun onResume() {
        super.onResume()
        binding.shimmerLayout.startShimmer()
    }

    override fun onPause() {
        binding.shimmerLayout.stopShimmer()
        super.onPause()
    }

    override fun onDestroyView() {
        with(binding) {
            historyRecyclerView.clearOnScrollListeners()
            historyRecyclerView.adapter = null
        }

        super.onDestroyView()
    }

    companion object {
        fun getScreen() = FragmentScreen { RandomJokeFragment() }
    }
}