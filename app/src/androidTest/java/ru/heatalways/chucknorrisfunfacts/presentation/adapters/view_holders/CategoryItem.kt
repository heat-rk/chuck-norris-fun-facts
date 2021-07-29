package ru.heatalways.chucknorrisfunfacts.presentation.adapters.view_holders

import android.view.View
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.text.KButton
import org.hamcrest.Matcher

class CategoryItem(parent: Matcher<View>): KRecyclerItem<CategoryItem>(parent) {
    val button = KButton { withMatcher(parent) }
}