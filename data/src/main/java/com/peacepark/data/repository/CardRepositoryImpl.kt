package com.peacepark.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.peacepark.data.mapper.mapToData
import com.peacepark.data.mapper.mapToDomainWithPaging
import com.peacepark.data.model.CardEntity
import com.peacepark.data.repository.local.CardLocalDataSource
import com.peacepark.data.utils.Constants.PAGING_SIZE
import com.peacepark.domain.model.Card
import com.peacepark.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    private val cardLocalDataSource: CardLocalDataSource
): CardRepository {

    override suspend fun saveCard(card: Card) {
        try {
            cardLocalDataSource.insertCard(mapToData(card))
        } catch (e: Exception) {

        }

    }

    override fun getCards(): Flow<PagingData<Card>> {
        val pagingSourceFactory = {
            cardLocalDataSource.getAllCards()
        }

        return Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE,
                enablePlaceholders = false,
                maxSize = PAGING_SIZE * 3
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map { cardEntity ->
                mapToDomainWithPaging(cardEntity)
            }
        }
    }
}