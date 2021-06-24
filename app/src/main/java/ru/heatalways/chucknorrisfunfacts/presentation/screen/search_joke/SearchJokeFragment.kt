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
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseFragment

@AndroidEntryPoint
class SearchJokeFragment: BaseFragment<FragmentSearchJokeBinding>() {
    private val viewModel: SearchJokeViewModel by viewModels()
    private val jokesAdapter = JokesAdapter()

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentSearchJokeBinding {
        return FragmentSearchJokeBinding.inflate(layoutInflater, container, false)
    }

    override val contentId = R.id.recyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(R.string.search_joke_screen_title)

        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = jokesAdapter

            searchView.onSearchExecute = { searchQuery ->
                viewModel.onSearchQueryExecute(searchQuery)
            }
        }

        initJokesObserver()
    }

    private fun initJokesObserver() {
        observe(viewModel.state) { state ->
            when (state) {
                is SearchJokeState.Error -> {
                    setProgressBarVisibility(false)
                    setErrorVisibility(true, state.message)
                }
                is SearchJokeState.Loaded -> {
                    setProgressBarVisibility(false)
                    setErrorVisibility(false)
                    jokesAdapter.submitList(state.jokes)
                }
                SearchJokeState.Loading -> {
                    setProgressBarVisibility(true)
                    setErrorVisibility(false)
                }
            }
        }
    }

    companion object {
        fun getScreen() = FragmentScreen { SearchJokeFragment() }
    }
}