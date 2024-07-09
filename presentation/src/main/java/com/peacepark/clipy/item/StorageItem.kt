package com.peacepark.clipy.item

import android.net.Uri

data class StorageItem(
    val cardCode: String,
    val name: String,
    val rank: String,
    val company: String,
    val cardImage: String,
    val addedDate: String,
    val badge: Boolean
)
