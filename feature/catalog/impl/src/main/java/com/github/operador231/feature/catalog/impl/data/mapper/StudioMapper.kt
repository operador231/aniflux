package com.github.operador231.feature.catalog.impl.data.mapper

import com.github.operador231.core.database.entity.StudioEntity
import com.github.operador231.core.network.graphql.GetAnimeListQuery
import com.github.operador231.core.network.graphql.GetAnimeQuery

internal fun GetAnimeQuery.Studio.toEntity(): StudioEntity = StudioEntity(
    id = this.id.toInt(),
    name = this.name,
    imageUrl = this.imageUrl ?: ""
)

internal fun GetAnimeListQuery.Studio.toEntity(): StudioEntity = StudioEntity(
    id = this.id.toInt(),
    name = this.name,
    imageUrl = this.imageUrl ?: ""
)