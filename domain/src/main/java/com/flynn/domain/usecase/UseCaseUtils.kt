package com.flynn.domain.usecase

import com.flynn.domain.resource.Resource
import kotlinx.coroutines.flow.Flow

suspend fun <T, R> executeUseCase(
    useCase: Flow<Resource<T>>,
    onLoading: (suspend () -> R)? = null,
    onSuccess: (suspend (data: T) -> R)? = null,
    onFailure: (suspend (error: Error) -> R)? = null,
) {
    useCase.collect { result ->
        when (result) {
            is Resource.Loading -> onLoading?.invoke()
            is Resource.Failed -> onFailure?.invoke(result.error)
            is Resource.Success -> onSuccess?.invoke(result.data)
        }
    }
}
