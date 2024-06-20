package com.example.clipy.ui.home

import androidx.lifecycle.ViewModel
import com.peacepark.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

}