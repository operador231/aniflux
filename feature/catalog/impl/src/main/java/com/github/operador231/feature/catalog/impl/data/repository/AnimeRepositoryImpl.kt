package com.github.operador231.feature.catalog.impl.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.github.operador231.core.database.AniFluxDatabase
import com.github.operador231.core.domain.model.Anime
import com.github.operador231.core.domain.model.Media
import com.github.operador231.core.domain.model.MediaId
import com.github.operador231.core.domain.repository.AnimeRepository
import com.github.operador231.feature.catalog.impl.data.mapper.toPreview
import com.github.operador231.feature.catalog.impl.data.remote.AnimeRemoteDataSource
import com.github.operador231.feature.catalog.impl.data.remote.AnimeRemoteMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

public class AnimeRepositoryImpl @Inject constructor(
    private val remoteDataSource: AnimeRemoteDataSource,
    private val db: AniFluxDatabase
) : AnimeRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getAnime(
        page: Int?,
        limit: Int?
    ): Flow<PagingData<Media>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 5,
                enablePlaceholders = true
            ),
            remoteMediator = AnimeRemoteMediator(
                remoteDataSource = remoteDataSource,
                db = db
            ),
            pagingSourceFactory = {
                db.animeDao().getAnime()
            }
        ).flow.map { data ->
            data.map { it.toPreview() }
        }
    }

    override fun getAnime(id: MediaId): Flow<Anime> {
        TODO("Not yet implemented")
    }
}