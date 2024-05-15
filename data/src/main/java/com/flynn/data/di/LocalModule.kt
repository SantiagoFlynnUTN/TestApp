package com.flynn.data.di

import android.content.Context
import com.flynn.data.local.room.CompassDb
import com.flynn.data.local.room.LocalDatabaseResource
import com.flynn.data.local.room.LocalDatabaseResourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    internal fun provideDatabase(
        @ApplicationContext context: Context
    ): CompassDb {
        return CompassDb.create(
            applicationContext = context
        )
    }

    @Singleton
    @Provides
    internal fun provideLocalDatabaseResource(
        compassDb: CompassDb
    ): LocalDatabaseResource {
        return LocalDatabaseResourceImpl(
            compassDb = compassDb
        )
    }
}