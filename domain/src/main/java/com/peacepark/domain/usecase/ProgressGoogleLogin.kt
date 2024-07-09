package com.peacepark.domain.usecase

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import com.peacepark.domain.repository.AuthRepository
import javax.inject.Inject

class ProgressGoogleLogin @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(account: GoogleSignInAccount?): Result<Boolean> = authRepository.googleLogin(account)
}