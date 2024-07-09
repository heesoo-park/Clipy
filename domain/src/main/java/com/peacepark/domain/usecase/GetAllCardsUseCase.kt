package com.peacepark.domain.usecase

import androidx.paging.PagingData
import com.peacepark.domain.model.Card
import com.peacepark.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCardsUseCase @Inject constructor(
    private val cardRepository: CardRepository
) {
    operator fun invoke(): Flow<PagingData<Card>> {
        return cardRepository.getCards()
    }
}