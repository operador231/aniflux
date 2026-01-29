package com.github.operador231.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "genre"
)
public data class GenreEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val kindId: Int
)
