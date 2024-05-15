package com.flynn.data.usecase

import com.flynn.data.repository.RepositoryImpl
import com.flynn.domain.usecase.ClearLocalDataUseCase

class ClearLocalDataUseCaseImpl(
    val repository: RepositoryImpl
): ClearLocalDataUseCase {
    override suspend operator fun invoke() {
        repository.clearLocalData()
    }
}