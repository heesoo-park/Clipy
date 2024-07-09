package com.peacepark.clipy.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.peacepark.data.database.card.CardDao
import com.peacepark.data.database.card.CardDatabase
import com.peacepark.data.repository.CardRepositoryImpl
import com.peacepark.data.repository.local.CardLocalDataSource
import com.peacepark.data.repository.local.CardLocalDataSourceImpl
import com.peacepark.data.repository.remote.AuthRepositoryImpl
import com.peacepark.data.repository.remote.StorageRepositoryImpl
import com.peacepark.domain.repository.AuthRepository
import com.peacepark.domain.repository.CardRepository
import com.peacepark.domain.repository.StorageRepository
import com.peacepark.domain.usecase.CheckLoginState
import com.peacepark.domain.usecase.GetAllCardsUseCase
import com.peacepark.domain.usecase.ProgressGoogleLogin
import com.peacepark.domain.usecase.ProgressLogin
import com.peacepark.domain.usecase.ProgressLogout
import com.peacepark.domain.usecase.ProgressSignup
import com.peacepark.domain.usecase.SaveBusinessCardUseCase
import com.peacepark.domain.usecase.UploadCardImageUseCase
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
    fun provideFirebaseStorage() = FirebaseStorage.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository = AuthRepositoryImpl(firebaseAuth)

    @Provides
    @Singleton
    fun provideStorageRepository(firebaseStorage: FirebaseStorage): StorageRepository = StorageRepositoryImpl(firebaseStorage)

    @Provides
    fun provideProgressLogin(authRepository: AuthRepository): ProgressLogin = ProgressLogin(authRepository)

    @Provides
    fun provideProgressGoogleLogin(authRepository: AuthRepository): ProgressGoogleLogin = ProgressGoogleLogin(authRepository)

    @Provides
    fun provideProgressSignup(authRepository: AuthRepository): ProgressSignup = ProgressSignup(authRepository)

    @Provides
    fun provideCheckLoginState(authRepository: AuthRepository): CheckLoginState = CheckLoginState(authRepository)

    @Provides
    fun provideProgressLogout(authRepository: AuthRepository): ProgressLogout = ProgressLogout(authRepository)

    @Provides
    fun provideUploadCardImageUseCase(storageRepository: StorageRepository): UploadCardImageUseCase = UploadCardImageUseCase(storageRepository)

    @Provides
    @Singleton
    fun provideCardDao(cardDatabase: CardDatabase): CardDao = cardDatabase.cardDao()

    @Provides
    @Singleton
    fun provideCardDatabase(@ApplicationContext context: Context): CardDatabase = Room.databaseBuilder(
        context,
        CardDatabase::class.java,
        "Card.db"
    ).build()

    @Provides
    @Singleton
    fun provideCardLocalDataSource(cardDao: CardDao): CardLocalDataSource {
        return CardLocalDataSourceImpl(cardDao)
    }

    @Provides
    @Singleton
    fun provideCardRepository(cardLocalDataSource: CardLocalDataSource): CardRepository {
        return CardRepositoryImpl(cardLocalDataSource)
    }

    @Provides
    fun provideSaveBusinessCardUseCase(cardRepository: CardRepository): SaveBusinessCardUseCase = SaveBusinessCardUseCase(cardRepository)

    @Provides
    fun provideGetAllCardsUseCase(cardRepository: CardRepository): GetAllCardsUseCase = GetAllCardsUseCase(cardRepository)

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class AppSettingsSharedPreference

    @Provides
    @AppSettingsSharedPreference
    fun provideAppSettingsSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
    }
}