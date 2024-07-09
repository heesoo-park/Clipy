package com.peacepark.domain.usecase

import com.peacepark.domain.model.Card
import com.peacepark.domain.repository.CardRepository
import javax.inject.Inject

class SaveBusinessCardUseCase @Inject constructor(
    private val cardRepository: CardRepository
) {
    suspend operator fun invoke(card: Card) {
        cardRepository.saveCard(card)
    }
}