package com.github.operador231.feature.catalog.impl.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.apollographql.apollo.api.Optional
import com.github.operador231.core.database.AniFluxDatabase
import com.github.operador231.core.database.entity.AnimeRemoteKeysEntity
import com.github.operador231.core.database.model.AnimeAggregate
import com.github.operador231.core.network.model.NetworkResult
import com.github.operador231.feature.catalog.impl.data.mapper.toEntity
import timber.log.Timber

@OptIn(ExperimentalPagingApi::class)
public class AnimeRemoteMediator(
    private val remoteDataSource: AnimeRemoteDataSource,
    private val db: AniFluxDatabase
) : RemoteMediator<Int, AnimeAggregate>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = 24 // 24 hrs
        val lastUpdated = db.remoteKeysDao().getLastAnimeRefreshTimestamp() ?: 0L
        val currentTimestamp = System.currentTimeMillis()

        return if (currentTimestamp - lastUpdated <= cacheTimeout * 60 * 60 * 1000) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AnimeAggregate>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKeys = db.remoteKeysDao().getLatestRemoteKey()
                    val nextPage = remoteKeys?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )

                    Timber.d("LoadType.APPEND nextPage=$nextPage")
                    nextPage
                }
            }

            val response = remoteDataSource.getAnime(
                page = Optional.Present(page),
                limit = Optional.Present(20)
            )
            Timber.d(response.toString())

            when (response) {
                is NetworkResult.Ok -> {
                    val animeDtos = response.value
                    val endOfPaginationReached = animeDtos.isEmpty()

                    val nextPage = page + 1
                    val keys = animeDtos.map { dto ->
                        AnimeRemoteKeysEntity(
                            id = dto.id.toInt(),
                            nextPage = nextPage,
                            timestamp = System.currentTimeMillis()
                        )
                    }

                    db.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            db.remoteKeysDao().clearAll()
                            db.animeDao().clearAll()
                            db.genreDao().clearAll()
                            db.studioDao().clearAll()
                        }

                        db.remoteKeysDao().upsertAnimeKeys(keys)

                        val baseIndex = (page - 1) * 20
                        animeDtos.forEachIndexed { idx, dto ->
                            db.animeDao().saveAnime(
                                anime = dto.toEntity(baseIndex + idx),
                                genres = dto.genres?.map { it.toEntity() } ?: emptyList(),
                                studios = dto.studios.map { it.toEntity() }
                            )
                        }
                    }

                    MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
                }
                is NetworkResult.Err -> return MediatorResult.Error(response.throwable)
            }
        } catch (ex: Exception) {
            MediatorResult.Error(ex)
        }
    }
}