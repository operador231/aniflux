package com.github.operador231.core.ui.extensions

import android.content.Context
import com.github.operador231.core.ui.R
import com.github.operador231.core.ui.model.Season

public fun Season.getLabel(ctx: Context): String = when (this) {
    Season.UNKNOWN -> ctx.getString(R.string.st_unknown)
    Season.WINTER -> ctx.getString(R.string.st_winter)
    Season.SPRING -> ctx.getString(R.string.st_spring)
    Season.SUMMER -> ctx.getString(R.string.st_summer)
    Season.AUTUMN -> ctx.getString(R.string.st_autumn)
}