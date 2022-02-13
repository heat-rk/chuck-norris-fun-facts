package ru.heatalways.chucknorrisfunfacts.common.general.utils.paging

sealed class PagingData<S, E> {
    data class Error<S, E>(val error: E): PagingData<S, E>()
    data class Success<S, E>(val body: List<S>): PagingData<S, E>()
}