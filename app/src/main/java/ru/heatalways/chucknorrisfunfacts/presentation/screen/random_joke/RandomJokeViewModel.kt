package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.heatalways.chucknorrisfunfacts.data.entities.Category
import ru.heatalways.chucknorrisfunfacts.domain.managers.chuck_norris_jokes.ChuckNorrisJokesManager
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseViewModel
import ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category.SelectCategoryFragment
import javax.inject.Inject

@HiltViewModel
class RandomJokeViewModel @Inject constructor(
    private val jokesManager: ChuckNorrisJokesManager,
    private val router: Router
): BaseViewModel() {

    private val mState = MutableLiveData<RandomJokeState>()
    val state: LiveData<RandomJokeState> = mState

    fun fetchRandomJoke(selectedCategory: Category) {
        viewModelScope.launch {
            mState.value = RandomJokeState.Loading

            val response = jokesManager.random(
                when (selectedCategory) {
                    is Category.Specific -> selectedCategory.name
                    else -> null
                }
            )

            if (response.isOk && response.value != null) {
                mState.value = RandomJokeState.Loaded(listOf(response.value))
            } else {
                mState.value = RandomJokeState.Error(response.error?.message)
            }
        }
    }

    fun navigateToCategorySelectionScreen() {
        router.navigateTo(SelectCategoryFragment.getScreen())
    }
}