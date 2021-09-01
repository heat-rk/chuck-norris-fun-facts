package ru.heatalways.chucknorrisfunfacts.domain.utils.paging

sealed class PagingEvent <S, E> {
    data class LoadError<S, E>(val error: E): PagingEvent<S, E>()
    data class Loaded<S, E>(val items: List<S>): PagingEvent<S, E>()

    object Updating: PagingEvent<Nothing, Nothing>()
    object UpdateError: PagingEvent<Nothing, Nothing>()
    data class Updated<S, E>(val items: List<S>): PagingEvent<S, E>()
}
