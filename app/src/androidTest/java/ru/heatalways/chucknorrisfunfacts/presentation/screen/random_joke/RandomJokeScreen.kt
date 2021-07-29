package ru.heatalways.chucknorrisfunfacts.screens

import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KButton
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.RandomJokeFragment
import ru.heatalways.chucknorrisfunfacts.recycler_items.JokeItem

object RandomJokeScreen: BaseScreen<RandomJokeScreen>() {
    override val layoutId = R.layout.fragment_random_joke
    override val viewClass = RandomJokeFragment::class.java

    val selectCategoryButton = KButton { withId(R.id.selectCategoryButton) }
    val geJokeButton = KButton { withId(R.id.getJokeButton) }
    val recyclerView = KRecyclerView({
            withId(R.id.historyRecyclerView)
        },
        {
            itemType(::JokeItem)
        }
    )
}