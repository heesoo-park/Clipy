package com.peacepark.data.database.card

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.peacepark.data.model.CardEntity
import com.peacepark.domain.model.Card

@Dao
interface CardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCard(card: CardEntity)

    @Query("DELETE FROM table_card WHERE name = :name")
    fun deleteCard(name: String)

    @Query("SELECT * FROM table_card")
    fun getAllCards(): PagingSource<Int, CardEntity>

    @Query("SELECT * FROM table_card WHERE company = :company")
    fun getAllCardsWithCompany(company: String): PagingSource<Int, CardEntity>

    @Query("SELECT * FROM table_card ORDER BY addedDate ASC")
    fun getAllSortedCardsWithAddedDate(): PagingSource<Int, CardEntity>
}