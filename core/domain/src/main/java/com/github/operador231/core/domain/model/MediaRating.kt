package com.github.operador231.core.domain.model

/**
 * Represents the rating (age restrictions) of a media item.
 * */
public enum class MediaRating(public val value: Int) {
    /**
     * An unknown rating. Used when the real rating is not known or available.
     * */
    UNKNOWN(-1),
    G(0),
    PG(1),
    PG_13(2),
    R(3),
    R_PLUS(4),
    RX(5);

    public companion object {
        public fun fromValue(value: Int): MediaRating {
            return entries.firstOrNull { it.value == value } ?: UNKNOWN
        }
    }
}