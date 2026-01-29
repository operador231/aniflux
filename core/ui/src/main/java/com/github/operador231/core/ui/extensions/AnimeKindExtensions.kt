package com.github.operador231.core.ui.extensions

import android.content.Context
import com.github.operador231.core.domain.model.AnimeKind
import com.github.operador231.core.ui.R

public fun AnimeKind.getLabel(ctx: Context): String = when (this) {
    AnimeKind.UNKNOWN -> ctx.getString(R.string.st_unknown)
    AnimeKind.TV -> ctx.getString(R.string.st_tv)
    AnimeKind.MOVIE -> ctx.getString(R.string.st_movie)
    AnimeKind.OVA -> ctx.getString(R.string.st_ova)
    AnimeKind.ONA -> ctx.getString(R.string.st_ona)
    AnimeKind.SPECIAL -> ctx.getString(R.string.st_special)
    AnimeKind.TV_SPECIAL -> ctx.getString(R.string.st_tv_special)
    AnimeKind.MUSIC -> ctx.getString(R.string.st_music)
    AnimeKind.PV -> ctx.getString(R.string.st_pv)
    AnimeKind.CM -> ctx.getString(R.string.st_cm)
}