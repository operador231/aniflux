package com.github.operador231.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.github.operador231.core.database.entity.AnimeRemoteKeysEntity

@Dao
public interface RemoteKeysDao {

    @Query("SELECT * FROM anime_remote_keys WHERE id = :id")
    public suspend fun getAnimeRemoteKeys(id: Int): AnimeRemoteKeysEntity?

    @Query("SELECT * FROM anime_remote_keys ORDER BY nextPage DESC LIMIT 1")
    public suspend fun getLatestRemoteKey(): AnimeRemoteKeysEntity?

    @Query("SELECT timestamp FROM anime_remote_keys ORDER BY timestamp DESC LIMIT 1")
    public suspend fun getLastAnimeRefreshTimestamp(): Long?

    @Query("DELETE FROM anime_remote_keys")
    public suspend fun clearAll()

    @Upsert
    public suspend fun upsertAnimeKeys(keys: List<AnimeRemoteKeysEntity>)
}