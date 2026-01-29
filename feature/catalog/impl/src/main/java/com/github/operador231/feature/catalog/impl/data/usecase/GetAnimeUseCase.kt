package com.github.operador231.feature.catalog.impl.data.usecase

import androidx.paging.PagingData
import com.github.operador231.core.domain.model.Media
import com.github.operador231.core.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow

public class GetAnimeUseCase(
    private val animeRepository: AnimeRepository
) {
    internal operator fun invoke(
        page: Int = 1,
        limit: Int = 20
    ): Flow<PagingData<Media>> = animeRepository.getAnime(
        page = page,
        limit = limit
    )
}