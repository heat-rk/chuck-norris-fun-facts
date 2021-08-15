package ru.heatalways.chucknorrisfunfacts.domain.interactors.search_joke

import ru.heatalways.chucknorrisfunfacts.presentation.base.MviAction

sealed class SearchJokeAction: MviAction {
    class OnSearchExecute(val query: String): SearchJokeAction()
}
