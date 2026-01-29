package com.github.operador231.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "studio"
)
public data class StudioEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String
)
