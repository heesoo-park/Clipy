package com.peacepark.data.database.card

import androidx.room.Database
import androidx.room.RoomDatabase
import com.peacepark.data.model.CardEntity

@Database(entities = [CardEntity::class], version = 1)
abstract class CardDatabase: RoomDatabase() {
    abstract fun cardDao(): CardDao
}