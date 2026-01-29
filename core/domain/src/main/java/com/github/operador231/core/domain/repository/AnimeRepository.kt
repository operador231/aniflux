package com.github.operador231.core.domain.repository

import androidx.paging.PagingData
import com.github.operador231.core.domain.model.Anime
import com.github.operador231.core.domain.model.Media
import com.github.operador231.core.domain.model.MediaId
import kotlinx.coroutines.flow.Flow

public interface AnimeRepository {

    public fun getAnime(
        page: Int? = null,
        limit: Int? = null
    ): Flow<PagingData<Media>>

    public fun getAnime(id: MediaId): Flow<Anime>
}