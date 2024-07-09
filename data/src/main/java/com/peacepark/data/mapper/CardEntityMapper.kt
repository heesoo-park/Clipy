package com.peacepark.data.mapper

import com.peacepark.data.model.CardEntity
import com.peacepark.domain.model.Card

fun mapToDomainWithPaging(entity: CardEntity): Card = Card(
    cardCode = entity.cardCode,
    name = entity.name,
    rank = entity.rank,
    company = entity.company,
    cardImage = entity.cardImage,
    addedDate = entity.addedDate,
    badge = entity.badge
)

fun mapToData(model: Card): CardEntity = CardEntity(
    cardCode = model.cardCode,
    name = model.name,
    rank = model.rank,
    company = model.company,
    cardImage = model.cardImage,
    addedDate = model.addedDate
)