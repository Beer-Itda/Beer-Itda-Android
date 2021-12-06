package com.hjiee.domain

sealed class NetworkResponse<T> {
    data class Success<T>(val data: T) : NetworkResponse<T>()
    data class Error<T>(val error: Throwable) : NetworkResponse<T>()
    class NoContents<T> : NetworkResponse<T>()
    class Loading<T> : NetworkResponse<T>()
}

typealias NetworkCallback<T> = (NetworkResponse<T>) -> Unit