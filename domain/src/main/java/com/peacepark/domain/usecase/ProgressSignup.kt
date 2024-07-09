package com.peacepark.domain.usecase

import com.google.firebase.auth.FirebaseUser
import com.peacepark.domain.repository.AuthRepository
import javax.inject.Inject

class ProgressSignup @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun invoke(email: String, password: String): Result<FirebaseUser> = authRepository.signup(email, password)
}