package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseViewModel
import ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category.SelectCategoryFragment

class RandomJokeViewModel: BaseViewModel() {
    private val mSelectedCategory = MutableLiveData<String?>(null)
    val selectedCategory: LiveData<String?> = mSelectedCategory

    private val mState = MutableLiveData<RandomJokeState>()
    val state: LiveData<RandomJokeState> = mState

    fun fetchRandomJoke() {
        viewModelScope.launch {
            mState.value = RandomJokeState.JokeLoading
            val response = jokesService.random(selectedCategory.value)
            if (response.isOk && response.value != null) {
                mState.value = RandomJokeState.JokesLoaded(listOf(response.value))
            } else {
                mState.value = RandomJokeState.JokeLoadError(response.error?.message)
            }
        }
    }

    fun selectCategory(category: String?) {
        mSelectedCategory.value = category
    }

    fun navigateToCategorySelectionScreen() {
        router.navigateTo(SelectCategoryFragment.getScreen())
    }
}