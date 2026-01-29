package com.github.operador231.feature.catalog.impl.data.remote

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.github.operador231.core.data.di.IODispatcher
import com.github.operador231.core.data.exceptions.NotFoundException
import com.github.operador231.core.domain.model.MediaId
import com.github.operador231.core.network.graphql.GetAnimeListQuery
import com.github.operador231.core.network.graphql.GetAnimeQuery
import com.github.operador231.core.network.model.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * A remote data source for fetching anime data from the network.
 *
 * This class is responsible for making GraphQL queries to retrieve anime information
 * using an [ApolloClient].
 *
 * @param apolloClient The [ApolloClient] instance used for making GraphQL queries.
 * @param dispatcher The [CoroutineDispatcher] on which network operations will be executed,
 * typically an IO dispatcher.
 */
public class AnimeRemoteDataSource @Inject constructor(
    private val apolloClient: ApolloClient,
    @param:IODispatcher private val dispatcher: CoroutineDispatcher
) {

    /**
     * Fetches a list of anime.
     *
     * Executes the [GetAnimeListQuery] and wraps the result in a [NetworkResult].
     *
     * @param page The optional page number to fetch.
     * @param limit The optional limit for the number of items per page.
     *
     * @return A [NetworkResult.Ok] containing a list of [GetAnimeListQuery.Anime] on success,
     * or a [NetworkResult.Err] containing an exception on failure. Returns an empty
     * list if the response data is null.
     */
    public suspend fun getAnime(
        page: Optional<Int?>,
        limit: Optional<Int?>
    ): NetworkResult<List<GetAnimeListQuery.Anime>> = withContext(dispatcher) {
        try {
            val response = apolloClient.query(GetAnimeListQuery(
                page = page,
                limit = limit
            )).execute()

            if (response.hasErrors()) {
                NetworkResult.Err(Exception(response.errors?.firstOrNull()?.message))
            } else {
                val data = response.data?.animes ?: emptyList()
                NetworkResult.Ok(data)
            }
        } catch (ex: Exception) {
            NetworkResult.Err(ex)
        }
    }

    /**
     * Fetches a single anime by its unique identifier.
     *
     * Executes the [GetAnimeQuery] with the given [id] and wraps the result in a [NetworkResult].
     *
     * @param id The [MediaId] of the anime to fetch.
     * @return A [NetworkResult.Ok] containing the [GetAnimeQuery.Anime] on success
     * or a [NetworkResult.Err] containing an exception on failure.
     */
    public suspend fun getAnime(id: MediaId): NetworkResult<GetAnimeQuery.Anime> = withContext(dispatcher) {
        try {
            val response = apolloClient.query(GetAnimeQuery(id.toString())).execute()
            if (response.hasErrors()) {
                NetworkResult.Err(Exception(response.errors?.firstOrNull()?.message))
            } else {
                val data = response.data?.animes?.firstOrNull()
                    ?: throw NotFoundException("Anime not found")

                NetworkResult.Ok(data)
            }
        } catch (ex: Exception) {
            NetworkResult.Err(ex)
        }
    }
}