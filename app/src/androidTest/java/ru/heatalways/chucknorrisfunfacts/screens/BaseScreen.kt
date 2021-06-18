package ru.heatalways.chucknorrisfunfacts.screens

import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.bottomnav.KBottomNavigationView
import ru.heatalways.chucknorrisfunfacts.R

abstract class BaseScreen<T: KScreen<T>>: KScreen<T>() {
    val bottomNavigation = KBottomNavigationView {
        withId(R.id.bottomNavigationBar)
    }
}