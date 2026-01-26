package com.github.operador231.core.domain.model

/**
 * Represents the current status of a media item (e.g. Anime, Manga, Ranobe)
 * */
public enum class MediaStatus(public val value: Int) {

    /**
     * An unknown status. Used when the real status is not known or available.
     * */
    UNKNOWN(-1),
    ANNOUNCEMENT(0),
    ONGOING(1),
    RELEASED(2),
    PAUSED(3),
    DISCONTINUED(4);

    public companion object {

        /**
         * Returns the [MediaStatus] corresponding to the given [value].
         * If no matching [MediaStatus] is found, [UNKNOWN] is returned.
         *
         * @param value The integer value of the [MediaStatus].
         * @return The corresponding [MediaStatus]
         * */
        public fun fromValue(value: Int): MediaStatus {
            return entries.firstOrNull { it.value == value } ?: UNKNOWN
        }
    }
}