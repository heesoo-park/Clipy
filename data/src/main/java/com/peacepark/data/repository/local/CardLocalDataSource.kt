package com.peacepark.data.repository.local

import androidx.paging.PagingSource
import com.peacepark.data.model.CardEntity
import com.peacepark.domain.model.Card

interface CardLocalDataSource {
    fun insertCard(card: CardEntity)
    fun deleteCard(name: String)
    fun getAllCards(): PagingSource<Int, CardEntity>
    fun getAllCardsWithCompany(company: String): PagingSource<Int, CardEntity>
    fun getAllSortedCardsWithAddedDate(): PagingSource<Int, CardEntity>
}