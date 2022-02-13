package ru.heatalways.chucknorrisfunfacts.presentation.screen.search_joke

import io.github.kakaocup.kakao.recycler.KRecyclerView
import ru.heatalways.chucknorrisfunfacts.presentation.custom_view.KSearchQueryView
import ru.heatalways.chucknorrisfunfacts.presentation.adapters.view_holders.JokeItem
import ru.heatalways.chucknorrisfunfacts.presentation.screen.BaseScreen
import ru.heatalways.chucknorrisfunfacts.search_joke.presentation.screen.search_joke.SearchJokeFragment
import ru.heatalways.navigation.R

object SearchJokeScreen: BaseScreen<SearchJokeScreen>() {
    override val layoutId = R.layout.fragment_search_joke
    override val viewClass = SearchJokeFragment::class.java

    val searchQueryView = KSearchQueryView { withId(R.id.searchView) }
    val recyclerView = KRecyclerView(
        {
            withId(R.id.jokesRecyclerView)
        },
        {
            itemType(::JokeItem)
        }
    )
}