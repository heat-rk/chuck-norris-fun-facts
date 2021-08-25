package ru.heatalways.chucknorrisfunfacts.data.network.util

import com.google.gson.JsonParseException
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> ResultWrapper.NetworkError
                is HttpException -> {
                    val code = throwable.code()
                    convertErrorBody(code, throwable)
                }
                else -> {
                    ResultWrapper.UnknownError()
                }
            }
        }
    }
}

private fun <T> convertErrorBody(code: Int, throwable: HttpException): ResultWrapper<T> {
    return throwable.response()?.errorBody()?.let {
        parseErrorBody(it, code)
    } ?: ResultWrapper.UnknownError(code)
}

private fun <T> parseErrorBody(errorBody: ResponseBody?, code: Int): ResultWrapper<T> {
    val errorMessage = errorBody?.string() ?: ""

    return try {
        val errorJson = JsonParser().parse(errorMessage).asJsonObject
        ResultWrapper.GenericError(
            status = errorJson.get(STATUS).asInt,
            message = errorJson.get(MESSAGE).asString,
            timestamp = errorJson.get(TIMESTAMP).asString,
            error = errorJson.get(ERROR).asString
        )
    } catch (e: JsonParseException) {
        return ResultWrapper.UnknownError(code)
    } catch (e: JsonSyntaxException) {
        return ResultWrapper.UnknownError(code)
    }
}

private const val TIMESTAMP = "timestamp"
private const val STATUS = "status"
private const val ERROR = "error"
private const val MESSAGE = "message"