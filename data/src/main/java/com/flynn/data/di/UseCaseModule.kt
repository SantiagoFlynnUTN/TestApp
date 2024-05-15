package com.flynn.data.di

import com.flynn.data.repository.RepositoryImpl
import com.flynn.data.usecase.ClearLocalDataUseCaseImpl
import com.flynn.data.usecase.GetAboutPageContentUseCaseImpl
import com.flynn.domain.usecase.ClearLocalDataUseCase
import com.flynn.domain.usecase.GetAboutPageContentUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideGetAboutPageContentUseCase(
        repository: RepositoryImpl
    ): GetAboutPageContentUseCase = GetAboutPageContentUseCaseImpl(
        repository = repository
    )

    @Provides
    fun provideClearLocalDataUseCase(
        repository: RepositoryImpl
        ): ClearLocalDataUseCase = ClearLocalDataUseCaseImpl(
        repository = repository
    )
}