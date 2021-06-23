package ru.heatalways.chucknorrisfunfacts.screens

import io.github.kakaocup.kakao.recycler.KRecyclerView
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.presentation.screen.search_joke.SearchJokeFragment
import ru.heatalways.chucknorrisfunfacts.recycler_items.JokeItem
import ru.heatalways.chucknorrisfunfacts.views.KSearchQueryView

object SearchJokeScreen: BaseScreen<SearchJokeScreen>() {
    override val layoutId = R.layout.fragment_search_joke
    override val viewClass = SearchJokeFragment::class.java

    val searchQueryView = KSearchQueryView { withId(R.id.searchView) }
    val recyclerView = KRecyclerView(
        {
            withId(R.id.recyclerView)
        },
        {
            itemType(::JokeItem)
        }
    )
}