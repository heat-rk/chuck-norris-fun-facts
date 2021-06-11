package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.heatalways.chucknorrisfunfacts.data.entities.Category
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseViewModel
import ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category.SelectCategoryFragment

class RandomJokeViewModel: BaseViewModel() {
    private val mSelectedCategory = MutableLiveData<Category>(Category.Any)
    val selectedCategory: LiveData<Category> = mSelectedCategory

    private val mState = MutableLiveData<RandomJokeState>()
    val state: LiveData<RandomJokeState> = mState

    fun fetchRandomJoke() {
        viewModelScope.launch {
            mState.value = RandomJokeState.JokeLoading

            selectedCategory.value.let { category ->
                val response = jokesService.random(
                    when (category) {
                        is Category.Specific -> category.name
                        else -> null
                    }
                )

                if (response.isOk && response.value != null) {
                    mState.value = RandomJokeState.JokesLoaded(listOf(response.value))
                } else {
                    mState.value = RandomJokeState.JokeLoadError(response.error?.message)
                }
            }
        }
    }

    fun selectCategory(category: Category) {
        mSelectedCategory.value = category
    }

    fun navigateToCategorySelectionScreen() {
        router.navigateTo(SelectCategoryFragment.getScreen())
    }
}