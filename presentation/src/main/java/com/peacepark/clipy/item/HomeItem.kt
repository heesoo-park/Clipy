package com.peacepark.clipy.item

import android.graphics.Bitmap
import android.net.Uri

data class HomeItem(
    val isCard: Boolean,
    val cardUri: Uri?,
    var qrBitmap: Bitmap?,
)
