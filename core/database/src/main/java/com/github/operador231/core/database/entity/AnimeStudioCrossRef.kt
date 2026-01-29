package com.github.operador231.core.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "anime_studio_cross_ref",
    primaryKeys = ["animeId", "studioId"],
    foreignKeys = [
        ForeignKey(
            entity = AnimeEntity::class,
            parentColumns = ["id"],
            childColumns = ["animeId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = StudioEntity::class,
            parentColumns = ["id"],
            childColumns = ["studioId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
public data class AnimeStudioCrossRef(
    val animeId: Int,
    val studioId: Int
)
