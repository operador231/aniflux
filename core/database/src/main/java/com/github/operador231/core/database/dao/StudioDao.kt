package com.github.operador231.core.database.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
public interface StudioDao {

    @Query("DELETE FROM studio")
    public suspend fun clearAll()
}