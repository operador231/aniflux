package com.github.operador231.core.database.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.github.operador231.core.database.entity.AnimeEntity
import com.github.operador231.core.database.entity.AnimeGenreCrossRef
import com.github.operador231.core.database.entity.AnimeStudioCrossRef
import com.github.operador231.core.database.entity.GenreEntity
import com.github.operador231.core.database.entity.StudioEntity

public data class AnimeAggregate(
    @Embedded val anime: AnimeEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = AnimeGenreCrossRef::class,
            parentColumn = "animeId",
            entityColumn = "genreId"
        )
    )
    val genres: List<GenreEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = AnimeStudioCrossRef::class,
            parentColumn = "animeId",
            entityColumn = "studioId"
        )
    )
    val studios: List<StudioEntity>
)
