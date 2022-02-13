package ru.heatalways.chucknorrisfunfacts.presentation.adapters.view_holders

import android.view.View
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher
import ru.heatalways.navigation.R

class JokeItem(parent: Matcher<View>): KRecyclerItem<JokeItem>(parent) {
    val image = KImageView(parent) { withId(R.id.iconImageView) }
    val text = KTextView(parent) { withId(R.id.jokeTextView) }
}