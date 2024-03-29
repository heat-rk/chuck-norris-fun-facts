package ru.heatalways.chucknorrisfunfacts.random_joke.presentation.screen.random_joke

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import ru.heatalways.chucknorrisfunfacts.common.extensions.*
import ru.heatalways.chucknorrisfunfacts.core.domain.models.Category
import ru.heatalways.chucknorrisfunfacts.core.domain.models.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.core.presentation.adapters.JokesAdapter
import ru.heatalways.chucknorrisfunfacts.core.presentation.base.MviFragment
import ru.heatalways.chucknorrisfunfacts.random_joke.R
import ru.heatalways.chucknorrisfunfacts.random_joke.di.RandomJokeFeatureComponentHolder
import ru.heatalways.chucknorrisfunfacts.common.general.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.common.presentation.factories.GenericSavedStateViewModelFactory
import ru.heatalways.chucknorrisfunfacts.common.presentation.utils.IndefiniteSnackbar
import ru.heatalways.chucknorrisfunfacts.common.presentation.utils.ScrollState
import ru.heatalways.chucknorrisfunfacts.common.presentation.utils.SnackbarState
import ru.heatalways.chucknorrisfunfacts.common.presentation.utils.ToastState
import ru.heatalways.chucknorrisfunfacts.core.databinding.FragmentRandomJokeBinding
import ru.heatalways.chucknorrisfunfacts.core.presentation.adapters.decorators.MarginItemDecoration
import ru.ldralighieri.corbind.appcompat.itemClicks
import ru.ldralighieri.corbind.view.clicks
import javax.inject.Inject

internal class RandomJokeFragment: MviFragment<
    RandomJokeAction,
    RandomJokeViewState
>(R.layout.fragment_random_joke) {

    @Inject
    lateinit var viewModelFactory: RandomJokeViewModel.Factory

    override val viewModel: RandomJokeViewModel by viewModels {
        GenericSavedStateViewModelFactory(viewModelFactory, this)
    }

    private val binding by viewBinding(FragmentRandomJokeBinding::bind)
    private val jokesAdapter = JokesAdapter()
    private val snackbar = IndefiniteSnackbar()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        RandomJokeFeatureComponentHolder.getComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.appbar.toolbar.setTitle(R.string.random_joke_screen_title)
        binding.appbar.toolbar.inflateMenu(R.menu.random_jokes_history_menu)

        with(binding.historyRecyclerView) {
            addItemDecoration(MarginItemDecoration(R.dimen.paddingMD))
            layoutManager = LinearLayoutManager(requireContext())
            adapter = jokesAdapter
        }

        findNavController().currentBackStackEntry
            ?.savedStateHandle?.let { handle ->
                handle.getLiveData<Category>("category")
                .observe(viewLifecycleOwner) { category ->
                    viewModel.selectCategory(category)
                }
            }
    }

    override val actions get() =
        merge(
            binding.getJokeButton.clicks()
                .map { RandomJokeAction.RequestRandomJoke },

            binding.selectCategoryButton.clicks()
                .map { RandomJokeAction.SelectCategory },

            binding.historyRecyclerView.scrollsToLastItem()
                .map { RandomJokeAction.NextPage },

            binding.appbar.toolbar.itemClicks()
                .debounceFirst(CLEAR_JOKES_DEBOUNCE)
                .throttleFirst(CLEAR_JOKES_THROTTLE)
                .map { RandomJokeAction.ToolbarItemSelect(it.itemId) },

            snackbar.actions
                .map { RandomJokeAction.RestoreJokes }
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
            if (message != null) binding.errorView.show(message)
            else binding.errorView.hide()
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
                    coroutineScope = viewLifecycleOwner.lifecycleScope
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
        private const val CLEAR_JOKES_DEBOUNCE = 1000L
        private const val CLEAR_JOKES_THROTTLE = 2000L
    }
}