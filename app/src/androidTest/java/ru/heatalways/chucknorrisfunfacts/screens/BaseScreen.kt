package ru.heatalways.chucknorrisfunfacts.screens

import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.bottomnav.KBottomNavigationView
import io.github.kakaocup.kakao.text.KTextView
import ru.heatalways.chucknorrisfunfacts.R

abstract class BaseScreen<T: KScreen<T>>: KScreen<T>() {
    val bottomNavigation = KBottomNavigationView {
        withId(R.id.bottomNavigationBar)
    }

    val errorTextView = KTextView {
        withId(R.id.errorTextView)
    }
}