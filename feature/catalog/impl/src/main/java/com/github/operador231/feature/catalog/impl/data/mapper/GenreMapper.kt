package com.github.operador231.feature.catalog.impl.data.mapper

import com.github.operador231.core.database.entity.GenreEntity
import com.github.operador231.core.domain.model.Genre
import com.github.operador231.core.domain.model.GenreId
import com.github.operador231.core.network.graphql.GetAnimeListQuery
import com.github.operador231.core.network.graphql.GetAnimeQuery

internal fun GetAnimeQuery.Genre.toEntity(): GenreEntity = GenreEntity(
    id = this.id.toInt(),
    name = this.name,
    kindId = -1
)

internal fun GetAnimeListQuery.Genre.toEntity(): GenreEntity = GenreEntity(
    id = this.id.toInt(),
    name = this.name,
    kindId = -1
)

internal fun GenreEntity.toDomain(): Genre = Genre(
    id = GenreId(this.id.toUInt()),
    name = this.name
)