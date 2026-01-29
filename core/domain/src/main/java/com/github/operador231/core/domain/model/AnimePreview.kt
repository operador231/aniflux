package com.github.operador231.core.domain.model

import kotlinx.collections.immutable.ImmutableList

/**
 * A preview model of an anime for lists or grids.
 *
 * @property status The current status of an anime. For more details, see [MediaStatus].
 * @property type The type of the anime. For more details, see [AnimeKind].
 * */
public data class AnimePreview(
    override val id: MediaId,
    override val title: String,
    override val description: String,
    override val genres: ImmutableList<Genre>,
    override val posterUrl: String,
    override val score: Float,
    override val status: MediaStatus,
    val rating: MediaRating,
    val type: AnimeKind,
    val season: String,
    val airYear: Int,
    val airMonth: Int
): Media
