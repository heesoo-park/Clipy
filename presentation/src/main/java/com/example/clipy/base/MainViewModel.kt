package com.example.clipy.base

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clipy.type.PageType
import com.example.clipy.R

class MainViewModel: ViewModel() {
    private val _curPageType: MutableLiveData<PageType> = MutableLiveData(PageType.PAGE1)
    val curPageType: LiveData<PageType> = _curPageType

    private val _curQRCode: MutableLiveData<Bitmap> = MutableLiveData()
    val curQRCode: LiveData<Bitmap> get() = _curQRCode

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
}