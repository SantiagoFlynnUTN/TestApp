package com.flynn.data.repository

import com.flynn.data.local.room.LocalDatabaseResource
import com.flynn.data.remote.NetworkDataSource
import com.flynn.data.utils.fetchDataLocalAndRemote
import com.flynn.domain.resource.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val localDatabaseResource: LocalDatabaseResource,
    private val networkDataSource: NetworkDataSource,
) {

    suspend fun getContent(): Flow<Resource<String>> {
        return fetchDataLocalAndRemote(
            localDataSource = { localDatabaseResource.getContent() },
            remoteDataSource = { networkDataSource.getContent() },
            onResourceUpdated = { content -> localDatabaseResource.saveContent(content) }
        )
    }

    suspend fun clearLocalData() {
        localDatabaseResource.clearLocalData()
    }
}