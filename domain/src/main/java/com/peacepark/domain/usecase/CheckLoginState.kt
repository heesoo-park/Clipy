package com.peacepark.domain.usecase

import com.google.firebase.auth.FirebaseUser
import com.peacepark.domain.repository.AuthRepository

class CheckLoginState(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Boolean = authRepository.isLogin()
}