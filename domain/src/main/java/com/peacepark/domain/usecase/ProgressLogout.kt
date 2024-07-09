package com.peacepark.domain.usecase

import com.peacepark.domain.repository.AuthRepository
import javax.inject.Inject

class ProgressLogout @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke() = authRepository.logout()
}