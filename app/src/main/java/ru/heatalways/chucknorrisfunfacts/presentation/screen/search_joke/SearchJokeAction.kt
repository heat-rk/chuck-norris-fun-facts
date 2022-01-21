package ru.heatalways.chucknorrisfunfacts.presentation.screen.search_joke

import ru.heatalways.chucknorrisfunfacts.core.base.MviAction

sealed class SearchJokeAction: MviAction {
    class OnSearchExecute(val query: String): SearchJokeAction()
}
