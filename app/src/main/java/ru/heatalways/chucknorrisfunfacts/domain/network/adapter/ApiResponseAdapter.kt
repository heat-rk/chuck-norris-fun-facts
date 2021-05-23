package ru.heatalways.chucknorrisfunfacts.domain.network.adapter

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import ru.heatalways.chucknorrisfunfacts.domain.network.api_response.ApiResponse
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ApiResponseAdapter<T: Any>(
    private val successType: Type
) : CallAdapter<T, Call<ApiResponse<T>>> {
    override fun responseType(): Type = successType

    override fun adapt(call: Call<T>): Call<ApiResponse<T>> {
        return ApiResponseCall(call)
    }

    class Factory : CallAdapter.Factory() {
        override fun get(
            returnType: Type,
            annotations: Array<Annotation>,
            retrofit: Retrofit
        ): CallAdapter<*, *>? {

            // suspend functions wrap the response type in `Call`
            if (Call::class.java != getRawType(returnType)) {
                return null
            }

            // check first that the return type is `ParameterizedType`
            check(returnType is ParameterizedType) {
                "return type must be parameterized as Call<ApiResponse<<Foo>> or Call<ApiResponse<out Foo>>"
            }

            // get the response type inside the `Call` type
            val responseType = getParameterUpperBound(0, returnType)
            // if the response type is not ApiResponse then we can't handle this type, so we return null
            if (getRawType(responseType) != ApiResponse::class.java) {
                return null
            }

            // the response type is ApiResponse and should be parameterized
            check(responseType is ParameterizedType) { "Response must be parameterized as ApiResponse<Foo> or ApiResponse<out Foo>" }

            val successBodyType = getParameterUpperBound(0, responseType)

            return ApiResponseAdapter<Any>(successBodyType)
        }
    }
}