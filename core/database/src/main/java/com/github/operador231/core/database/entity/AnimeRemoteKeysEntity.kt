package com.github.operador231.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "anime_remote_keys"
)
public data class AnimeRemoteKeysEntity(
    @PrimaryKey val id: Int,
    val nextPage: Int?,
    val timestamp: Long?
)
