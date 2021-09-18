package ru.heatalways.chucknorrisfunfacts.presentation.screen.main

import ru.heatalways.chucknorrisfunfacts.presentation.base.MviAction

sealed class MainAction: MviAction {
    data class OnBottomItemChange(val itemId: Int): MainAction()
}