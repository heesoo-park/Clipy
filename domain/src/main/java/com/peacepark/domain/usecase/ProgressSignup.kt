package com.peacepark.domain.usecase

import com.google.firebase.auth.FirebaseUser
import com.peacepark.domain.repository.AuthRepository

class ProgressSignup(
    private val authRepository: AuthRepository
) {
    suspend fun invoke(email: String, password: String): Result<FirebaseUser> = authRepository.signup(email, password)
}