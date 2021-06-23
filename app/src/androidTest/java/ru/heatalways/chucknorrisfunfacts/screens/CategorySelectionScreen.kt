package ru.heatalways.chucknorrisfunfacts.screens

import io.github.kakaocup.kakao.recycler.KRecyclerView
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category.SelectCategoryFragment
import ru.heatalways.chucknorrisfunfacts.recycler_items.CategoryItem
import ru.heatalways.chucknorrisfunfacts.views.KSearchQueryView

object CategorySelectionScreen: BaseScreen<CategorySelectionScreen>() {
    override val layoutId = R.layout.fragment_select_category
    override val viewClass = SelectCategoryFragment::class.java

    val searchQueryView = KSearchQueryView { withId(R.id.searchView) }
    val categories = KRecyclerView(
        {
            withId(R.id.categoriesRecyclerView)
        },
        {
            itemType(::CategoryItem)
        }
    )
}