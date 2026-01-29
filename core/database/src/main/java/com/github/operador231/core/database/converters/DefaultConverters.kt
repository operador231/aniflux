package com.github.operador231.core.database.converters

import androidx.room.TypeConverter
import kotlinx.collections.immutable.ImmutableList
import kotlinx.serialization.json.Json

public class DefaultConverters {

    @TypeConverter
    public fun fromStringList(value: ImmutableList<String>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    public fun toStringList(value: String): ImmutableList<String> {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    public fun fromIntList(value: ImmutableList<Int>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    public fun toIntList(value: String): ImmutableList<Int> {
        return Json.decodeFromString(value)
    }
}