package ru.heatalways.chucknorrisfunfacts.screens

import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.recycler.KRecyclerView
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.presentation.screen.search_joke.SearchJokeFragment
import ru.heatalways.chucknorrisfunfacts.recycler_items.JokeItem

object SearchJokeScreen: BaseScreen<SearchJokeScreen>() {
    override val layoutId = R.layout.fragment_search_joke
    override val viewClass = SearchJokeFragment::class.java

    val search = KEditText { withId(R.id.searchQueryEditText) }
    val searchButton = KImageView { withId(R.id.searchButton) }
    val recyclerView = KRecyclerView(
        {
            withId(R.id.recyclerView)
        },
        {
            itemType(::JokeItem)
        }
    )
}