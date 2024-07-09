package com.peacepark.domain.usecase

import com.peacepark.domain.repository.AuthRepository
import javax.inject.Inject

class CheckLoginState @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Boolean = authRepository.isLogin()
}