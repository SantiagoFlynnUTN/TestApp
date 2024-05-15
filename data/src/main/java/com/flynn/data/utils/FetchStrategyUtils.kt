package com.flynn.data.utils

import com.flynn.domain.resource.Resource.Failed
import com.flynn.domain.resource.Resource.Loading
import com.flynn.domain.resource.Resource.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import retrofit2.Response


fun <T: Any> fetchDataLocalAndRemote(
    localDataSource: suspend () -> T?,
    remoteDataSource: suspend () -> Response<T>,
    onResourceUpdated: suspend (T) -> Unit,
) = channelFlow {

    send(Loading)

    var data: T? = null

    launch {
        localDataSource()?.let {
            if(data == null){
                data = it
                send(Success(it))
            }
        }
    }

    launch {
        try {
            remoteDataSource().let {
                it.onSuccess { result ->
                    if (result != data) {
                        send(Success(result))
                        onResourceUpdated(result)
                    }
                }
                    //.onFailure { error -> send(Failed(Error(error))) }
            }
        } catch (e: Exception) {
            send(Failed(Error(e.message ?: "Unknown error")))
        }
    }
}.flowOn(Dispatchers.IO)