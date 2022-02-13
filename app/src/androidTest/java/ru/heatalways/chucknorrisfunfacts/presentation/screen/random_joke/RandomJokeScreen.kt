package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke

import io.github.kakaocup.kakao.progress.KProgressBar
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KButton
import ru.heatalways.navigation.R
import ru.heatalways.chucknorrisfunfacts.presentation.adapters.view_holders.JokeItem
import ru.heatalways.chucknorrisfunfacts.presentation.screen.BaseScreen
import ru.heatalways.chucknorrisfunfacts.random_joke.presentation.screen.random_joke.RandomJokeFragment

object RandomJokeScreen: BaseScreen<RandomJokeScreen>() {
    override val layoutId = R.layout.fragment_random_joke
    override val viewClass = RandomJokeFragment::class.java

    val selectCategoryButton = KButton { withId(R.id.selectCategoryButton) }
    val geJokeButton = KButton { withId(R.id.getJokeButton) }
    val buttonProgressBar = KProgressBar { withId(R.id.buttonProgressBar) }
    val recyclerView = KRecyclerView({
            withId(R.id.historyRecyclerView)
        },
        {
            itemType(::JokeItem)
        }
    )
}