package ru.heatalways.chucknorrisfunfacts.domain.network.adapter

import com.google.gson.JsonParseException
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.*
import ru.heatalways.chucknorrisfunfacts.domain.network.api_response.ApiError
import ru.heatalways.chucknorrisfunfacts.domain.network.api_response.ApiResponse
import java.net.ConnectException
import java.net.UnknownHostException

internal class ApiResponseCall<T : Any>(
    private val delegate: Call<T>
) : Call<ApiResponse<T>> {

    override fun enqueue(callback: Callback<ApiResponse<T>>) {
        return delegate.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    if (response.body() != null)
                        callback.onResponse(
                            this@ApiResponseCall,
                            Response.success(ApiResponse(response))
                        )
                    else
                        callback.onResponse(
                            this@ApiResponseCall,
                            Response.success(ApiResponse(apiError = ApiError.ServerNotFoundError()))
                        )
                } else
                    callback.onResponse(
                        this@ApiResponseCall,
                        Response.success(parseErrorBody(response.errorBody(), response.code()))
                    )
            }

            override fun onFailure(call: Call<T>, e: Throwable) {
                e.printStackTrace()

                if(e is UnknownHostException || e is ConnectException) {
                    // Network disabled
                    callback.onResponse(
                        this@ApiResponseCall,
                        Response.success(ApiResponse(ApiError.NetworkConnectionError()))
                    )
                }

                if(e is HttpException) {
                    callback.onResponse(
                        this@ApiResponseCall,
                        Response.success(parseErrorBody(e.response()?.errorBody(), e.code()))
                    )
                }

                // Unknown error
                callback.onResponse(
                    this@ApiResponseCall,
                    Response.success(ApiResponse(ApiError.UnknownError(e.localizedMessage)))
                )
            }
        })
    }

    override fun timeout() = Timeout.NONE

    private fun parseErrorBody(errorBody: ResponseBody?, code: Int): ApiResponse<T> {
        val errorMessage = errorBody?.string() ?: ""

        return try {
            val errorJson = JsonParser().parse(errorMessage).asJsonObject
            ApiResponse(
                ApiError.Error(
                    status = errorJson.get(ApiResponse.STATUS).asInt,
                    message = errorJson.get(ApiResponse.MESSAGE).asString,
                    timestamp = errorJson.get(ApiResponse.TIMESTAMP).asString,
                    error = errorJson.get(ApiResponse.ERROR).asString
                )
            )
        } catch (e: JsonParseException) {
            return ApiResponse(ApiError.ServerUnknownError(code))
        } catch (e: JsonSyntaxException) {
            return ApiResponse(ApiError.ServerUnknownError(code))
        }
    }

    override fun isExecuted() = delegate.isExecuted

    override fun clone() = ApiResponseCall(delegate.clone())

    override fun isCanceled() = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<ApiResponse<T>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun request(): Request = delegate.request()
}