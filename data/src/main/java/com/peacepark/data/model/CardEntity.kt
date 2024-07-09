package com.peacepark.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_card")
data class CardEntity(
    @PrimaryKey
    val cardCode: String = "",
    val name: String = "",
    val rank: String = "",
    val company: String = "",
    val cardImage: String = "",
    val addedDate: String = "",
    val badge: Boolean = false
)