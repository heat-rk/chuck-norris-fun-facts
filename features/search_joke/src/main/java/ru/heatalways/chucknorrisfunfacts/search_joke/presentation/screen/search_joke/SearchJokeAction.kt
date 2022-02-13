package ru.heatalways.chucknorrisfunfacts.search_joke.presentation.screen.search_joke

import ru.heatalways.chucknorrisfunfacts.core.presentation.base.MviAction

internal sealed class SearchJokeAction: MviAction {
    class OnSearchExecute(val query: String): SearchJokeAction()
}
