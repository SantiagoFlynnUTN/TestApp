package com.flynn.data.di

import com.flynn.data.local.room.LocalDatabaseResource
import com.flynn.data.remote.NetworkDataSource
import com.flynn.data.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideRepository(
        localDatabaseResource: LocalDatabaseResource,
        networkDataSource: NetworkDataSource
    ): RepositoryImpl = RepositoryImpl(
        localDatabaseResource = localDatabaseResource,
        networkDataSource = networkDataSource
    )
}