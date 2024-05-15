package com.flynn.domain.usecase

interface ClearLocalDataUseCase {
    suspend operator fun invoke()
}