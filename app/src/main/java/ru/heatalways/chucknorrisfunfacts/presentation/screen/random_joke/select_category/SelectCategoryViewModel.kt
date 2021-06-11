package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.heatalways.chucknorrisfunfacts.data.entities.Category
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseViewModel
import ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.RandomJokeFragment

class SelectCategoryViewModel: BaseViewModel() {
    private val mState = MutableLiveData<SelectCategoryState>()
    val state: LiveData<SelectCategoryState> = mState

    init {
        viewModelScope.launch {
            mState.value = SelectCategoryState.CategoriesLoading
            val response = jokesService.categories()
            if (response.isOk && response.value != null) {
                val categories = listOf(Category.Any).plus(response.value.map {
                    Category.Specific(it)
                })
                mState.value = SelectCategoryState.CategoriesLoaded(categories)
            } else {
                mState.value = SelectCategoryState.CategoriesLoadError(response.error?.message)
            }
        }
    }

    fun backToRandomJokeScreen() {
        router.backTo(RandomJokeFragment.getScreen())
    }
}