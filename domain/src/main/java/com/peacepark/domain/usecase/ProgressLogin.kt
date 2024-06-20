package com.peacepark.domain.usecase

import com.google.firebase.auth.FirebaseUser
import com.peacepark.domain.repository.AuthRepository

class ProgressLogin(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<FirebaseUser> = authRepository.login(email, password)
}