package com.github.operador231.core.domain.model

import kotlinx.collections.immutable.ImmutableList

public data class Anime(
    override val id: MediaId,
    /** MyAnimeList ID */
    val malId: MediaId,
    /** Default title name in English */
    override val title: String,
    val titleRu: String,
    val titleJp: String,
    val synonyms: ImmutableList<String>,
    /** The title's description in HTML format */
    override val description: String,
    override val genres: ImmutableList<Genre>,
    override val posterUrl: String,
    override val score: Float,
    override val status: MediaStatus,
    val kind: AnimeKind,
    val rating: MediaRating,
    val episodesTotal: Int,
    val episodesAired: Int,
    val duration: Int,
    val releaseYear: Int,
    val releaseMonth: Int,
    val releaseDay: Int,
    val releaseSeason: String,
    val isCensored: Boolean,
    val studios: ImmutableList<Studio>,
    val screenshots: ImmutableList<AnimeScreenshot>
): Media
