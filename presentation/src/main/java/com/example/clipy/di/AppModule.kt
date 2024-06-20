package com.example.clipy.di

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.peacepark.data.repository.remote.AuthRepositoryImpl
import com.peacepark.domain.repository.AuthRepository
import com.peacepark.domain.usecase.CheckLoginState
import com.peacepark.domain.usecase.ProgressLogin
import com.peacepark.domain.usecase.ProgressLogout
import com.peacepark.domain.usecase.ProgressSignup
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository = AuthRepositoryImpl(firebaseAuth)

    @Provides
    fun provideProgressLogin(authRepository: AuthRepository): ProgressLogin = ProgressLogin(authRepository)

    @Provides
    fun provideProgressSignup(authRepository: AuthRepository): ProgressSignup = ProgressSignup(authRepository)

    @Provides
    fun provideCheckLoginState(authRepository: AuthRepository): CheckLoginState = CheckLoginState(authRepository)

    @Provides
    fun provideProgressLogout(authRepository: AuthRepository): ProgressLogout = ProgressLogout(authRepository)

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class AppSettingsSharedPreference

    @Provides
    @AppSettingsSharedPreference
    fun provideAppSettingsSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
    }
}