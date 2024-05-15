package com.flynn.data.utils

import retrofit2.Response

inline fun <T : Any> Response<T>.onSuccess(action: (T) -> Unit): Response<T> {
    if (isSuccessful) body()?.let(action)
    return this
}

inline fun <T : Any> Response<T>.onFailure(action: (String) -> Unit): Response<T> {
    if (!isSuccessful) {
        val errorMessage = errorBody()?.string() ?: message()
        action(errorMessage)
    }
    return this
}

