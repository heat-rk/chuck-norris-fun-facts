package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.heatalways.chucknorrisfunfacts.data.entities.Category
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseViewModel

class SharedCategorySelectionViewModel: BaseViewModel() {
    private val mSelectedCategory = MutableLiveData<Category>(Category.Any)
    val selectedCategory: LiveData<Category> = mSelectedCategory

    fun selectCategory(category: Category) {
        mSelectedCategory.value = category
    }
}