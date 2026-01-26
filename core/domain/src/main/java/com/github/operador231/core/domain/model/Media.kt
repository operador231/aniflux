package com.github.operador231.core.domain.model

import kotlinx.collections.immutable.ImmutableList

/**
 * A common contract for media items.
 * */
public interface Media {
    public val id: MediaId

    /** Localized title preferred for display. */
    public val title: String

    /** Plain text summary. */
    public val description: String
    public val genres: ImmutableList<Genre>
    public val posterUrl: String
    public val score: Float
    public val status: MediaStatus
}