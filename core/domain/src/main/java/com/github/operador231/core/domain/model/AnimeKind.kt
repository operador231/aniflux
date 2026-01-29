package com.github.operador231.core.domain.model

/**
 * Represents the type of an anime.
 *
 * @property value The integer representation of the anime type.
 */
public enum class AnimeKind(public val value: Int) {
    /** An unknown or unclassified anime type. */
    UNKNOWN(-1),

    /** A television series. */
    TV(0),

    /** A feature-length film. */
    MOVIE(1),

    /** An Original Video Animation, released directly to home video. */
    OVA(2),

    /** An Original Net Animation, released directly online. */
    ONA(3),

    /** A special episode, often supplementary to a main series. */
    SPECIAL(4),

    /** A special episode that aired on television. */
    TV_SPECIAL(5),

    /** A music video. */
    MUSIC(6),

    /** A promotional video. */
    PV(7),

    /** A commercial message or advertisement. */
    CM(8);

    public companion object {
        /**
         * Returns the [AnimeKind] corresponding to the given [value].
         * If no matching [AnimeKind] is found, [UNKNOWN] is returned.
         *
         * @param value The integer value of the [AnimeKind].
         * @return The corresponding [AnimeKind]
         * */
        public fun fromValue(value: Int): AnimeKind {
            return entries.firstOrNull { it.value == value } ?: UNKNOWN
        }
    }
}