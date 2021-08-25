package ru.heatalways.chucknorrisfunfacts.data.network.util

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T): ResultWrapper<T>()
    
    data class GenericError(
        val timestamp: String? = null,
        val error: String? = null,
        val message: String? = null,
        val status: Int = -1
    ): ResultWrapper<Nothing>()

    class UnknownError(
        val code: Int? = null
    ): ResultWrapper<Nothing>()

    object NetworkError: ResultWrapper<Nothing>()
}