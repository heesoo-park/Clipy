package com.example.clipy.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clipy.type.LoginState
import com.peacepark.domain.usecase.ProgressLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val progressLogin: ProgressLogin
): ViewModel() {

    private val _uiState: MutableStateFlow<LoginState> = MutableStateFlow(LoginState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    fun onLoginBtnClicked(email: String, password: String) {
        viewModelScope.launch {
            progressLogin(email, password).onSuccess {
                _uiState.value = LoginState.SUCCESS
            }.onFailure {
                _uiState.value = LoginState.FAILURE
            }
        }
    }
}