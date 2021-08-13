package ru.heatalways.chucknorrisfunfacts.presentation.screen.search_joke

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.terrakok.cicerone.androidx.FragmentScreen
import dagger.hilt.android.AndroidEntryPoint
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.databinding.FragmentSearchJokeBinding
import ru.heatalways.chucknorrisfunfacts.presentation.adapters.JokesAdapter
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseMviFragment

@AndroidEntryPoint
class SearchJokeFragment: BaseMviFragment<
        FragmentSearchJokeBinding,
        SearchJokeContract.Action,
        SearchJokeContract.State,
        SearchJokeContract.Effect
>() {
    override val viewModel: SearchJokeViewModel by viewModels()

    private val jokesAdapter = JokesAdapter()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchJokeBinding
        get() = FragmentSearchJokeBinding::inflate

    override val contentId = R.id.jokesRecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(R.string.search_joke_screen_title)

        binding.apply {
            jokesRecyclerView.layoutManager = LinearLayoutManager(context)
            jokesRecyclerView.adapter = jokesAdapter

            searchView.onSearchExecute = { searchQuery ->
                action(SearchJokeContract.Action.OnSearchExecute(searchQuery))
            }
        }
    }

    override fun renderState(state: SearchJokeContract.State) {
        jokesAdapter.submitList(state.jokes) {
            binding.jokesRecyclerView.scrollToPosition(0)
        }

        setProgressBarVisibility(state.isLoading)

        setErrorVisibility(state.message != null, state.message)
    }

    override fun handleEffect(effect: SearchJokeContract.Effect) = Unit

    companion object {
        fun getScreen() = FragmentScreen { SearchJokeFragment() }
    }
}