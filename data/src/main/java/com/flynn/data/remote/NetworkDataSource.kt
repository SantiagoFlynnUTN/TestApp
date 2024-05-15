package com.flynn.data.remote

import retrofit2.Response

interface NetworkDataSource {
    suspend fun getContent(): Response<String>
}

class NetworkDataSourceImpl(
    private val compassApi: CompassApi,
) : NetworkDataSource {
    override suspend fun getContent(): Response<String> {
        return compassApi.getContent()
    }
}