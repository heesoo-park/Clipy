package com.example.clipy.base

import android.content.SharedPreferences
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clipy.type.PageType
import com.example.clipy.R
import com.example.clipy.di.AppModule
import com.peacepark.domain.repository.AuthRepository
import com.peacepark.domain.usecase.CheckLoginState
import com.peacepark.domain.usecase.ProgressLogout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @AppModule.AppSettingsSharedPreference private val appSettings: SharedPreferences,
    private val checkLoginState: CheckLoginState,
    private val progressLogout: ProgressLogout
): ViewModel() {
    private val _curPageType: MutableLiveData<PageType> = MutableLiveData(PageType.PAGE1)
    val curPageType: LiveData<PageType> = _curPageType

    private val _curQRCode: MutableLiveData<Bitmap> = MutableLiveData()
    val curQRCode: LiveData<Bitmap> get() = _curQRCode

    private var _loginState: Boolean = false
    val loginState get() = _loginState

    init {
        _loginState = checkLoginState()
    }

    fun setCurrentPage(menuItemId: Int): Boolean {
        val pageType = getPageType(menuItemId)
        changeCurrentPage(pageType)

        return true
    }

    private fun getPageType(menuItemId: Int): PageType {
        return when (menuItemId) {
            R.id.navigation_qr -> PageType.PAGE1
            R.id.navigation_home -> PageType.PAGE2
            R.id.navigation_storage -> PageType.PAGE3
            else -> throw IllegalArgumentException("not found menu item id")
        }
    }

    private fun changeCurrentPage(pageType: PageType) {
        if (curPageType.value == pageType) return

        _curPageType.value = pageType
    }

    fun updateQRCode(qrCode: Bitmap) {
        _curQRCode.value = qrCode
    }

    fun logout() {
        progressLogout()
        _loginState = checkLoginState()
    }

    companion object {
        const val LOGIN_FLAG = "login_flag"
    }
}