package ru.heatalways.chucknorrisfunfacts.data.network.util.api_response

import retrofit2.Response

class ApiResponse<T> {
    val value: T?

    val error: ApiError?

    var code: Int

    val isOk: Boolean

    constructor(response: Response<out T>) {
        this.value = response.body()
        this.error = null
        this.code = response.code()
        this.isOk = response.isSuccessful
    }

    constructor(apiError: ApiError?) {
        this.value = null
        this.error = apiError
        this.code = apiError?.status ?: -1
        this.isOk = false
    }

    object ErrorMessage {
        const val NETWORK_DISABLED = "network_disabled"
        const val NOT_FOUND = "not_found"
        const val UNKNOWN_SERVER_ERROR = "unknown_server_error"
    }

    companion object {
        const val TIMESTAMP = "timestamp"
        const val STATUS = "status"
        const val ERROR = "error"
        const val MESSAGE = "message"
    }
}