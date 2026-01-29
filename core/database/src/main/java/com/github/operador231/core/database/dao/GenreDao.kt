package com.github.operador231.core.database.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
public interface GenreDao {

    @Query("DELETE FROM genre")
    public suspend fun clearAll()
}