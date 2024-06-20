package com.peacepark.domain.usecase

import com.google.firebase.auth.FirebaseUser
import com.peacepark.domain.repository.AuthRepository

class ProgressLogout(
    private val authRepository: AuthRepository
) {
    operator fun invoke() = authRepository.logout()
}