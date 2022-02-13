package ru.heatalways.chucknorrisfunfacts.common.general.utils.paging

sealed class PagingEvent <S, E> {
    data class LoadError<S, E>(val error: E): PagingEvent<S, E>()
    data class Loaded<S, E>(val items: List<S>): PagingEvent<S, E>()

    class Updating<S, E>: PagingEvent<S, E>()
    data class UpdateError<S, E>(val error: E): PagingEvent<S, E>()
    data class Updated<S, E>(val items: List<S>): PagingEvent<S, E>()
}
