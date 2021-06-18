package ru.heatalways.chucknorrisfunfacts.screens

import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KButton
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category.SelectCategoryFragment
import ru.heatalways.chucknorrisfunfacts.recycler_items.CategoryItem

object CategorySelectionScreen: BaseScreen<CategorySelectionScreen>() {
    override val layoutId = R.layout.fragment_select_category
    override val viewClass = SelectCategoryFragment::class.java

    val searchEditText = KEditText { withId(R.id.searchQueryEditText) }
    val searchButton = KButton { withId(R.id.searchButton) }
    val categories = KRecyclerView(
        {
            withId(R.id.categoriesRecyclerView)
        },
        {
            itemType(::CategoryItem)
        }
    )
}