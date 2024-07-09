package com.peacepark.data.repository.local

import androidx.paging.PagingSource
import com.peacepark.data.database.card.CardDao
import com.peacepark.data.model.CardEntity
import com.peacepark.domain.model.Card
import javax.inject.Inject

class CardLocalDataSourceImpl @Inject constructor(
    private val cardDao: CardDao
): CardLocalDataSource {
    override fun insertCard(card: CardEntity) {
        cardDao.insertCard(card)
    }

    override fun deleteCard(name: String) {
        cardDao.deleteCard(name)
    }

    override fun getAllCards(): PagingSource<Int, CardEntity> {
        return cardDao.getAllCards()
    }

    override fun getAllCardsWithCompany(company: String): PagingSource<Int, CardEntity> {
        return cardDao.getAllCardsWithCompany(company)
    }

    override fun getAllSortedCardsWithAddedDate(): PagingSource<Int, CardEntity> {
        return cardDao.getAllSortedCardsWithAddedDate()
    }
}