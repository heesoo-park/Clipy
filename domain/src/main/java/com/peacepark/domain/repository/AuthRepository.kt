package com.peacepark.domain.repository

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<FirebaseUser>
    suspend fun googleLogin(account: GoogleSignInAccount?): Result<Boolean>

    suspend fun signup(email: String, password: String): Result<FirebaseUser>

    fun isLogin(): Boolean

    fun logout()
}