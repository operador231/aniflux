package com.github.operador231.core.ui.extensions

import android.content.Context
import com.github.operador231.core.domain.model.MediaStatus
import com.github.operador231.core.ui.R

public fun MediaStatus.getLabel(ctx: Context): String = when (this) {
    MediaStatus.UNKNOWN -> ctx.getString(R.string.st_unknown)
    MediaStatus.ANNOUNCEMENT -> ctx.getString(R.string.st_announcement)
    MediaStatus.ONGOING -> ctx.getString(R.string.st_ongoing)
    MediaStatus.RELEASED -> ctx.getString(R.string.st_released)
    MediaStatus.PAUSED -> ctx.getString(R.string.st_paused)
    MediaStatus.DISCONTINUED -> ctx.getString(R.string.st_discontinued)
}