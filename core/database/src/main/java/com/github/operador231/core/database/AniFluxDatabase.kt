package com.github.operador231.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.operador231.core.database.converters.DefaultConverters
import com.github.operador231.core.database.dao.AnimeDao
import com.github.operador231.core.database.dao.GenreDao
import com.github.operador231.core.database.dao.RemoteKeysDao
import com.github.operador231.core.database.dao.StudioDao
import com.github.operador231.core.database.entity.AnimeEntity
import com.github.operador231.core.database.entity.AnimeGenreCrossRef
import com.github.operador231.core.database.entity.AnimeRemoteKeysEntity
import com.github.operador231.core.database.entity.AnimeStudioCrossRef
import com.github.operador231.core.database.entity.GenreEntity
import com.github.operador231.core.database.entity.StudioEntity

@Database(
    entities = [
        AnimeEntity::class,
        GenreEntity::class,
        AnimeGenreCrossRef::class,
        StudioEntity::class,
        AnimeStudioCrossRef::class,
        AnimeRemoteKeysEntity::class
               ],
    version = 1,
    exportSchema = true
)
@TypeConverters(DefaultConverters::class)
public abstract class AniFluxDatabase : RoomDatabase() {

    public abstract fun animeDao(): AnimeDao

    public abstract fun genreDao(): GenreDao

    public abstract fun studioDao(): StudioDao

    public abstract fun remoteKeysDao(): RemoteKeysDao
}