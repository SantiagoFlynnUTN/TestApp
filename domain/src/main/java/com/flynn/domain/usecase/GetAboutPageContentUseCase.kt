package com.flynn.domain.usecase

import com.flynn.domain.resource.Resource
import kotlinx.coroutines.flow.Flow

interface GetAboutPageContentUseCase {
    suspend operator fun invoke(): Flow<Resource<String>>
}