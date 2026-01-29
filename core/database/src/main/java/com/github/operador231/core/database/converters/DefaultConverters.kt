package com.github.operador231.core.database.converters

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

public class DefaultConverters {

    @TypeConverter
    public fun fromStringList(value: List<String>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    public fun toStringList(value: String): List<String> {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    public fun fromIntList(value: List<Int>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    public fun toIntList(value: String): List<Int> {
        return Json.decodeFromString(value)
    }
}