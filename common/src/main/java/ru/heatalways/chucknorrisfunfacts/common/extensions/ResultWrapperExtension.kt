package ru.heatalways.chucknorrisfunfacts.common.extensions

import ru.heatalways.chucknorrisfunfacts.common.data.utils.ResultNetwork

suspend fun <Body, Result> ResultNetwork<Body>.handle(
    onSuccess: suspend (Body) -> Result,
    onFailed: suspend (ResultNetwork.Error) -> Result
) = when (this) {
    is ResultNetwork.Error ->
        onFailed(this)

    is ResultNetwork.Success ->
        onSuccess(value)
}

suspend fun <Body, Result> ResultNetwork<Body>.onSuccess(
    func: suspend (Body) -> Result
) {
    if (this is ResultNetwork.Success) func(value)
}
