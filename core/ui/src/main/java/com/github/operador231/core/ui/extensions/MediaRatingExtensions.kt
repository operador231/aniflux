package com.github.operador231.core.ui.extensions

import android.content.Context
import com.github.operador231.core.domain.model.MediaRating
import com.github.operador231.core.ui.R

public fun MediaRating.getLabel(ctx: Context): String = when (this) {
    MediaRating.UNKNOWN -> ctx.getString(R.string.st_unknown)
    MediaRating.G -> ctx.getString(R.string.st_rating_g)
    MediaRating.PG -> ctx.getString(R.string.st_rating_pg)
    MediaRating.PG_13 -> ctx.getString(R.string.st_rating_pg_13)
    MediaRating.R -> ctx.getString(R.string.st_rating_r)
    MediaRating.R_PLUS -> ctx.getString(R.string.st_rating_r_plus)
    MediaRating.RX -> ctx.getString(R.string.st_rating_rx)
}