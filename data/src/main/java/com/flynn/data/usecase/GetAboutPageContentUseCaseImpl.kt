package com.flynn.data.usecase

import com.flynn.data.repository.RepositoryImpl
import com.flynn.domain.resource.Resource
import com.flynn.domain.usecase.GetAboutPageContentUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAboutPageContentUseCaseImpl @Inject constructor(
    private val repository: RepositoryImpl
): GetAboutPageContentUseCase {
    override suspend operator fun invoke(): Flow<Resource<String>> = repository.getContent()
}