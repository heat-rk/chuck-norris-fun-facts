package ru.heatalways.chucknorrisfunfacts.domain.interactors.utils

import ru.heatalways.chucknorrisfunfacts.data.network.util.ResultWrapper
import ru.heatalways.chucknorrisfunfacts.domain.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.domain.utils.strRes
import ru.heatalways.chucknorrisfunfacts.R

suspend fun <Body, Result> ResultWrapper<Body>.handle(
    onSuccess: suspend (Body) -> Result,
    onFailed: suspend (StringResource) -> Result
) = when (this) {
    is ResultWrapper.UnknownError ->
        onFailed(strRes(R.string.error_unknown))

    is ResultWrapper.GenericError ->
        onFailed(strRes(message))

    is ResultWrapper.NetworkError ->
        onFailed(strRes(R.string.error_network))

    is ResultWrapper.Success ->
        onSuccess(value)
}
