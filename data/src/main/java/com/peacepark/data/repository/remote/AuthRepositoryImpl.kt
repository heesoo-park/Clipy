package com.peacepark.data.repository.remote

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.play.integrity.internal.al
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.peacepark.data.DataNullException
import com.peacepark.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await


class AuthRepositoryImpl(
    private val auth: FirebaseAuth
): AuthRepository {

    override suspend fun login(
        email: String,
        password: String
    ): Result<FirebaseUser> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val user = result.user
            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(DataNullException())
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun googleLogin(account: GoogleSignInAccount?): Result<Boolean> {
        return try {
            val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
            auth.signInWithCredential(credential).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signup(
        email: String,
        password: String
    ): Result<FirebaseUser> {
//        return try {
//            val result = auth.createUserWithEmailAndPassword(email, password).await()
//            val user = result.user
//            if (user != null) {
//                Result.success(user)
//            } else {
//                Result.failure(error("dkfj"))
//            }
//        } catch (e: FirebaseAuthException) {
//            when (e.errorCode) {
//                "ERROR_EMAIL_ALREADY_IN_USE" -> {
//                    Result.failure(e)
//                }
//                else -> {
//                    Result.failure(e)
//                }
//            }
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
        return Result.success(auth.signInWithEmailAndPassword(email, password).await().user!!)
    }

    override fun isLogin(): Boolean {
        val user = auth.currentUser
        return user != null
    }

    override fun logout() {
        auth.signOut()
    }
}