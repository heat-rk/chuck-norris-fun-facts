package ru.heatalways.chucknorrisfunfacts.extensions

import ru.heatalways.chucknorrisfunfacts.data.network.util.ResultNetwork
import ru.heatalways.chucknorrisfunfacts.core.models.StringResource
import ru.heatalways.chucknorrisfunfacts.core.models.strRes
import ru.heatalways.chucknorrisfunfacts.R

suspend fun <Body, Result> ResultNetwork<Body>.handle(
    onSuccess: suspend (Body) -> Result,
    onFailed: suspend (StringResource) -> Result
) = when (this) {
    is ResultNetwork.UnknownError ->
        onFailed(strRes(R.string.error_unknown))

    is ResultNetwork.GenericError ->
        onFailed(strRes(message))

    is ResultNetwork.NetworkError ->
        onFailed(strRes(R.string.error_network))

    is ResultNetwork.Success ->
        onSuccess(value)
}

suspend fun <Body, Result> ResultNetwork<Body>.onSuccess(
    func: suspend (Body) -> Result
) {
    if (this is ResultNetwork.Success) func(value)
}
