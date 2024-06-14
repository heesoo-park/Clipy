package com.example.clipy.item

import android.net.Uri

data class StorageItem(
    val name: String,
    val rank: String,
    val company: String,
    val businessCard: Uri?,
    val badge: Boolean
)
