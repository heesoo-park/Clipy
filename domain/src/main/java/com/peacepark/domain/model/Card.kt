package com.peacepark.domain.model

data class Card(
    val cardCode: String,
    val name: String,
    val rank: String,
    val company: String,
    val cardImage: String,
    val addedDate: String,
    val badge: Boolean
)