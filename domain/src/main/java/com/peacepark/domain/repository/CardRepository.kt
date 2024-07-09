package com.peacepark.domain.repository

import androidx.paging.PagingData
import com.peacepark.domain.model.Card
import kotlinx.coroutines.flow.Flow

interface CardRepository {

    suspend fun saveCard(card: Card)
    fun getCards(): Flow<PagingData<Card>>
}