package com.github.operador231.feature.catalog.impl.data.mapper

import com.github.operador231.core.data.utils.BBCleaner
import com.github.operador231.core.database.entity.AnimeEntity
import com.github.operador231.core.database.model.AnimeAggregate
import com.github.operador231.core.domain.model.AnimeKind
import com.github.operador231.core.domain.model.AnimePreview
import com.github.operador231.core.domain.model.MediaId
import com.github.operador231.core.domain.model.MediaRating
import com.github.operador231.core.domain.model.MediaStatus
import com.github.operador231.core.network.graphql.GetAnimeListQuery
import com.github.operador231.core.network.graphql.GetAnimeQuery
import com.github.operador231.core.network.graphql.type.AnimeKindEnum
import com.github.operador231.core.network.graphql.type.AnimeRatingEnum
import com.github.operador231.core.network.graphql.type.AnimeStatusEnum
import kotlinx.collections.immutable.toImmutableList

internal fun AnimeStatusEnum.toMediaStatus(): MediaStatus = when (this) {
    AnimeStatusEnum.anons -> MediaStatus.ANNOUNCEMENT
    AnimeStatusEnum.ongoing -> MediaStatus.ONGOING
    AnimeStatusEnum.released -> MediaStatus.RELEASED
    AnimeStatusEnum.UNKNOWN__ -> MediaStatus.UNKNOWN
}

internal fun AnimeRatingEnum.toMediaRating(): MediaRating = when (this) {
    AnimeRatingEnum.g -> MediaRating.G
    AnimeRatingEnum.pg -> MediaRating.PG
    AnimeRatingEnum.pg_13 -> MediaRating.PG_13
    AnimeRatingEnum.r -> MediaRating.R
    AnimeRatingEnum.r_plus -> MediaRating.R_PLUS
    AnimeRatingEnum.rx -> MediaRating.RX
    AnimeRatingEnum.UNKNOWN__ -> MediaRating.UNKNOWN
    AnimeRatingEnum.none -> MediaRating.UNKNOWN
}

internal fun AnimeKindEnum.toAnimeKind(): AnimeKind = when (this) {
    AnimeKindEnum.tv -> AnimeKind.TV
    AnimeKindEnum.movie -> AnimeKind.MOVIE
    AnimeKindEnum.ova -> AnimeKind.OVA
    AnimeKindEnum.special -> AnimeKind.SPECIAL
    AnimeKindEnum.ona -> AnimeKind.ONA
    AnimeKindEnum.music -> AnimeKind.MUSIC
    AnimeKindEnum.tv_special -> AnimeKind.TV_SPECIAL
    AnimeKindEnum.cm -> AnimeKind.CM
    AnimeKindEnum.pv -> AnimeKind.PV
    AnimeKindEnum.UNKNOWN__ -> AnimeKind.UNKNOWN
}

internal fun GetAnimeQuery.Anime.toEntity(idx: Int): AnimeEntity {
    val entity = AnimeEntity(
        id = this.id.toInt(),
        malId = this.malId?.toInt() ?: -1,
        name = this.name,
        nameRu = this.russian ?: "unknown",
        nameJp = this.japanese ?: "unknown",
        kindId = this.kind?.toAnimeKind()?.value ?: AnimeKind.UNKNOWN.value,
        ratingId = this.rating?.toMediaRating()?.value ?: MediaRating.UNKNOWN.value,
        score = this.score ?: 0.0,
        statusId = this.status?.toMediaStatus()?.value ?: MediaStatus.UNKNOWN.value,
        episodesTotal = this.episodes,
        episodesAired = this.episodesAired,
        duration = this.duration ?: 0,
        airYear = this.airedOn?.year ?: -1,
        airMonth = this.airedOn?.month ?: -1,
        releaseYear = this.releasedOn?.year ?: -1,
        releaseMonth = this.releasedOn?.month ?: -1,
        releaseDay = this.releasedOn?.day ?: -1,
        releaseSeason = this.season ?: "unknown",
        posterPreview = this.poster?.preview2xUrl ?: "",
        posterOriginal = this.poster?.originalUrl ?: "",
        isCensored = this.isCensored ?: false,
        screenshots = this.screenshots.map { screenshot -> screenshot.originalUrl }, // todo: create table for screenshots?
        description = BBCleaner.clean(this.description),
        descriptionHtml = this.descriptionHtml ?: "",
        isFull = true,
        position = idx
    )

    return entity
}

internal fun GetAnimeListQuery.Anime.toEntity(idx: Int): AnimeEntity = AnimeEntity(
    id = this.id.toInt(),
    malId = this.malId?.toInt() ?: -1,
    name = this.name,
    nameRu = this.russian ?: "unknown",
    nameJp = "",
    kindId = this.kind?.toAnimeKind()?.value ?: AnimeKind.UNKNOWN.value,
    ratingId = this.rating?.toMediaRating()?.value ?: MediaRating.UNKNOWN.value,
    score = this.score ?: 0.0,
    statusId = this.status?.toMediaStatus()?.value ?: MediaStatus.UNKNOWN.value,
    episodesTotal = -1,
    episodesAired = -1,
    duration = 0,
    airYear = this.airedOn?.year ?: -1,
    airMonth = this.airedOn?.month ?: -1,
    releaseYear = this.releasedOn?.year ?: -1,
    releaseMonth = this.releasedOn?.month ?: -1,
    releaseDay = this.releasedOn?.day ?: -1,
    releaseSeason = this.season ?: "",
    posterPreview = this.poster?.main2xUrl ?: "",
    posterOriginal = "",
    isCensored = this.isCensored ?: false,
    screenshots = emptyList(),
    description = BBCleaner.clean(this.description),
    descriptionHtml = "",
    isFull = false,
    position = idx
)

internal fun AnimeAggregate.toPreview(): AnimePreview = AnimePreview(
    id = MediaId(this.anime.id.toUInt()),
    title = this.anime.name,
    description = this.anime.description,
    genres = this.genres.map { genre -> genre.toDomain() }.toImmutableList(),
    posterUrl = this.anime.posterPreview,
    score = this.anime.score.toFloat(),
    rating = MediaRating.fromValue(this.anime.ratingId),
    status = MediaStatus.fromValue(this.anime.statusId),
    type = AnimeKind.fromValue(this.anime.kindId),
    season = this.anime.releaseSeason,
    airYear = this.anime.airYear,
    airMonth = this.anime.airMonth
)