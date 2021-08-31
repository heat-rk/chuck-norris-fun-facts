package ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.select_category

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.Category
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.ChuckNorrisJokesRepository
import ru.heatalways.chucknorrisfunfacts.domain.utils.strRes
import ru.heatalways.chucknorrisfunfacts.extensions.handle
import javax.inject.Inject

class CategorySelectionInteractorImpl @Inject constructor(
    val chuckNorrisJokesRepository: ChuckNorrisJokesRepository
): CategorySelectionInteractor {

    private var categories = emptyList<Category>()

    private val _selectedCategory = MutableStateFlow<Category>(Category.Any)
    override val selectedCategory: StateFlow<Category> = _selectedCategory

    override fun fetchCategories(): Flow<CategorySelectionPartialState> = flow {
        val response = chuckNorrisJokesRepository.categories()

        emit(response.handle(
            onFailed = {
                categories = emptyList()
                CategorySelectionPartialState.Message(it)
            },
            onSuccess = { categoriesStrings ->
                categories = listOf(Category.Any).plus(categoriesStrings)
                CategorySelectionPartialState.Categories(categories)
            }
        ))
    }

    override fun searchCategories(query: String): Flow<CategorySelectionPartialState> = flow {
        emit(CategorySelectionPartialState.Loading)

        val results = if (query.isEmpty())
            categories
        else
            categories.filter { it is Category.Specific && it.name.contains(query) }

        if (results.isNotEmpty())
            emit(CategorySelectionPartialState.Categories(
                results
            ))
        else
            emit(CategorySelectionPartialState.Message(
                strRes(R.string.error_not_found)
            ))
    }

    override fun selectCategory(category: Category) {
        _selectedCategory.value = category
    }
}