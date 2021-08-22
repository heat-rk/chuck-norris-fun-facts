package ru.heatalways.chucknorrisfunfacts.data.network.util.api_response

import android.util.Log
import androidx.viewbinding.BuildConfig

sealed class ApiError {

    var timestamp: String? = null
    var error: String? = null
    var message: String? = null
    var status: Int = -1

    protected fun log() {
        if (BuildConfig.DEBUG) Log.d(TAG, message ?: "Empty Api message!")
    }

    class ServerNotFoundError : ApiError() {
        init {
            this.message = ApiResponse.ErrorMessage.NOT_FOUND
            this.status = 404
            log()
        }
    }

    class NetworkConnectionError : ApiError() {
        init {
            this.message = ApiResponse.ErrorMessage.NETWORK_DISABLED
            log()
        }
    }

    class ServerUnknownError(code: Int): ApiError() {
        init {
            this.message = ApiResponse.ErrorMessage.UNKNOWN_SERVER_ERROR
            this.status = code
            log()
        }
    }

    class Error(
        timestamp: String?,
        error: String?,
        status: Int,
        message: String?
    ) : ApiError() {
        init {
            this.timestamp = timestamp
            this.error = error
            this.status = status
            this.message = message
            log()
        }
    }

    class UnknownError(message: String?) : ApiError() {
        init {
            this.message = message
            log()
        }
    }

    companion object {
        private val TAG = ApiError::class.java.simpleName
    }
}