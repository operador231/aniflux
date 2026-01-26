package com.github.operador231.core.domain.model

/**
 * Represents a genre for a media item.
 *
 * This data class is primarily used for holding genre data.
 *
 * @property id The unique identifier for the genre.
 * @property name The localized name of the genre.
 */
public data class Genre(
    public val id: GenreId,
    public val name: String
)
