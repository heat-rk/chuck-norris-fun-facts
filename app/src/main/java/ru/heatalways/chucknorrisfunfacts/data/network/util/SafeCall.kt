package ru.heatalways.chucknorrisfunfacts.data.network.util

import android.util.Log
import com.google.gson.JsonParseException
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import ru.heatalways.chucknorrisfunfacts.BuildConfig
import java.io.IOException

suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): ResultNetwork<T> {
    return withContext(dispatcher) {
        try {
            ResultNetwork.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            logError(throwable.message, throwable)
            when (throwable) {
                is IOException -> {
                    ResultNetwork.NetworkError
                }
                is HttpException -> {
                    val code = throwable.code()
                    convertErrorBody(code, throwable)
                }
                else -> {
                    ResultNetwork.UnknownError()
                }
            }
        }
    }
}

private fun logError(message: String?, throwable: Throwable) {
    if (BuildConfig.DEBUG) Log.e("SafeApiCall", message, throwable)
}

private fun <T> convertErrorBody(code: Int, throwable: HttpException): ResultNetwork<T> {
    return throwable.response()?.errorBody()?.let {
        parseErrorBody(it, code)
    } ?: ResultNetwork.UnknownError(code)
}

private fun <T> parseErrorBody(errorBody: ResponseBody?, code: Int): ResultNetwork<T> {
    val errorMessage = errorBody?.string() ?: ""

    return try {
        val errorJson = JsonParser().parse(errorMessage).asJsonObject
        ResultNetwork.GenericError(
            status = errorJson.get(STATUS).asInt,
            message = errorJson.get(MESSAGE).asString,
            timestamp = errorJson.get(TIMESTAMP).asString,
            error = errorJson.get(ERROR).asString
        )
    } catch (e: JsonParseException) {
        return ResultNetwork.UnknownError(code)
    } catch (e: JsonSyntaxException) {
        return ResultNetwork.UnknownError(code)
    }
}

private const val TIMESTAMP = "timestamp"
private const val STATUS = "status"
private const val ERROR = "error"
private const val MESSAGE = "message"