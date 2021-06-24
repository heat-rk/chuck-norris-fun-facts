package ru.heatalways.chucknorrisfunfacts.presentation.screen.search_joke

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.domain.managers.chuck_norris_jokes.ChuckNorrisJokesManager
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SearchJokeViewModel @Inject constructor(
    private val jokesManager: ChuckNorrisJokesManager
): BaseViewModel() {
    private val mState = MutableLiveData<SearchJokeState>()
    val state: LiveData<SearchJokeState> = mState

    fun onSearchQueryExecute(query: String) {
        viewModelScope.launch {
            mState.value = SearchJokeState.Loading
            val response = jokesManager.search(query)
            if (response.isOk && response.value != null) {
                if (response.value.isNotEmpty())
                    mState.value = SearchJokeState.Loaded(response.value)
                else
                    mState.value = SearchJokeState.Error(getString(R.string.error_not_found))
            } else {
                mState.value = SearchJokeState.Error(response.error?.message)
            }
        }
    }

}