package ru.heatalways.chucknorrisfunfacts.data.network.util

sealed class ResultNetwork<out T> {
    data class Success<out T>(val value: T): ResultNetwork<T>()
    
    data class GenericError(
        val timestamp: String? = null,
        val error: String? = null,
        val message: String? = null,
        val status: Int = -1
    ): ResultNetwork<Nothing>()

    class UnknownError(
        val code: Int? = null
    ): ResultNetwork<Nothing>()

    object NetworkError: ResultNetwork<Nothing>()
}