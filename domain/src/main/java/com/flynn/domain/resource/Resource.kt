package com.flynn.domain.resource

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()

    data object Loading : Resource<Nothing>()

    data class Failed(val error: Error) : Resource<Nothing>()
}