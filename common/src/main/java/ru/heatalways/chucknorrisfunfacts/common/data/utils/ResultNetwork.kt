package ru.heatalways.chucknorrisfunfacts.common.data.utils

sealed class ResultNetwork<out T> {
    data class Success<out T>(val value: T): ResultNetwork<T>()

    sealed class Error: ResultNetwork<Nothing>() {
        data class Generic(
            val timestamp: String? = null,
            val error: String? = null,
            val message: String? = null,
            val status: Int = -1
        ): Error()

        data class Unknown(
            val code: Int? = null
        ): Error()

        object Network: Error()
    }
}