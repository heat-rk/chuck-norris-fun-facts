package ru.heatalways.chucknorrisfunfacts.presentation.screen

import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.bottomnav.KBottomNavigationView
import io.github.kakaocup.kakao.progress.KProgressBar
import io.github.kakaocup.kakao.text.KTextView
import ru.heatalways.chucknorrisfunfacts.R

abstract class BaseScreen<T: KScreen<T>>: KScreen<T>() {
    val bottomNavigation = KBottomNavigationView {
        withId(R.id.bottomNavigationBar)
    }

    val errorTextView = KTextView {
        withId(R.id.errorTextView)
    }

    val progressBar = KProgressBar {
        withId(R.id.progressBar)
    }
}