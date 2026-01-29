package com.github.operador231.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.github.operador231.core.database.entity.AnimeEntity
import com.github.operador231.core.database.entity.AnimeGenreCrossRef
import com.github.operador231.core.database.entity.AnimeStudioCrossRef
import com.github.operador231.core.database.entity.GenreEntity
import com.github.operador231.core.database.entity.StudioEntity
import com.github.operador231.core.database.model.AnimeAggregate
import kotlinx.coroutines.flow.Flow

@Dao
public interface AnimeDao {

    /**
     * Fetches all anime existing in the database.
     *
     * @return A [PagingSource] of [AnimeAggregate]s.
     * */
    @Transaction
    @Query("SELECT * FROM anime")
    public fun getAnime(): PagingSource<Int, AnimeAggregate>

    /**
     * Fetches a single [AnimeAggregate] by its [id].
     *
     * @return A [Flow] of [AnimeAggregate] or null.
     * */
    @Transaction
    @Query("SELECT * FROM anime WHERE id = :id")
    public fun getAnime(id: Int): Flow<AnimeAggregate?>

    @Upsert
    public suspend fun upsertAnime(anime: AnimeEntity)

    @Upsert
    public suspend fun upsertAnime(anime: List<AnimeEntity>)

    @Query("DELETE FROM ANIME")
    public suspend fun clearAll()

    @Upsert
    public suspend fun upsertGenres(genres: List<GenreEntity>)

    @Upsert
    public suspend fun upsertGenreRefs(refs: List<AnimeGenreCrossRef>)

    @Query("DELETE FROM anime_genre_cross_ref WHERE animeId = :animeId")
    public suspend fun clearGenreRefs(animeId: Int)

    @Upsert
    public suspend fun upsertStudios(studios: List<StudioEntity>)

    @Upsert
    public suspend fun upsertStudioRefs(refs: List<AnimeStudioCrossRef>)

    @Query("DELETE FROM anime_studio_cross_ref WHERE animeId = :animeId")
    public suspend fun clearStudioRefs(animeId: Int)

    @Query("SELECT isFull FROM anime WHERE id = :id")
    public suspend fun isFull(id: Int): Boolean?

    /**
     * Updates or inserts an anime and synchronizes its relations with genres and studios.
     *
     * This is a heavy transaction that:
     * 1. Upserts the core [AnimeEntity].
     * 2. Clears all existing genre and studio associations for this anime.
     * 3. Upserts new [GenreEntity] and [StudioEntity] records.
     * 4. Re-establishes many-to-many relationships using cross-reference tables.
     *
     * @param anime The anime entity to be saved.
     * @param genres The complete list of genres associated with this anime.
     * @param studios The complete list of studios associated with this anime.
     */
    @Transaction
    public suspend fun saveAnime(
        anime: AnimeEntity,
        genres: List<GenreEntity>,
        studios: List<StudioEntity>
    ) {
        upsertAnime(anime)

        clearGenreRefs(anime.id)
        clearStudioRefs(anime.id)

        upsertGenres(genres)
        upsertGenreRefs(
            genres.map { genre ->
                AnimeGenreCrossRef(
                    animeId = anime.id,
                    genreId = genre.id
                )
            }
        )

        upsertStudios(studios)
        upsertStudioRefs(
            studios.map { studio ->
                AnimeStudioCrossRef(
                    animeId = anime.id,
                    studioId = studio.id
                )
            }
        )
    }
}