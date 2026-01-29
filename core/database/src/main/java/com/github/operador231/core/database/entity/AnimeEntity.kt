package com.github.operador231.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.collections.immutable.ImmutableList

@Entity(
    tableName = "anime"
)
public data class AnimeEntity(
    @PrimaryKey val id: Int,
    /** MyAnimeList ID. */
    val malId: Int,
    /** The title name in English. */
    val name: String,
    val nameRu: String,
    val nameJp: String,
    val kindId: Int,
    val ratingId: Int,
    val score: Float,
    val statusId: Int,
    val episodesTotal: Int,
    val episodesAired: Int,
    val duration: Int,
    val releaseYear: Int,
    val releaseMonth: Int,
    val releaseDay: Int,
    val releaseSeason: String,
    val posterPreview: String,
    val posterOriginal: String,
    val isCensored: Boolean,
    val genreId: Int,
    val studioId: Int,
    val screenshots: ImmutableList<String>,
    val description: String,
    val descriptionHtml: String,
    /** The flag indicating whether the anime is full or not. */
    val isFull: Boolean
)
