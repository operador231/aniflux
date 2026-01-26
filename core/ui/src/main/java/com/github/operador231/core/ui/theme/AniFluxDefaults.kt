package com.github.operador231.core.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

public val LocalAniFluxPaddings: ProvidableCompositionLocal<AniFluxPaddings> = staticCompositionLocalOf { AniFluxPaddings() }
public val LocalAniFluxSizes: ProvidableCompositionLocal<AniFluxSizes> = staticCompositionLocalOf { AniFluxSizes() }


/**
 * Standard padding and margin values.
 * Use these for spacing between elements or internal container offsets.
 */
@Immutable
public data class AniFluxPaddings(
    /** 4.dp - Micro-adjustments and tight spacing. */
    public val extraSmall: Dp = 4.dp,
    /** 8.dp - Small element spacing, logical grouping. */
    public val small: Dp = 8.dp,
    /** 16.dp - Default screen padding and standard gaps. */
    public val medium: Dp = 16.dp,
    /** 24.dp - Large sections or header spacing. */
    public val large: Dp = 24.dp,
    /** 32.dp - Significant visual separation. */
    public val extraLarge: Dp = 32.dp,
    /** 48.dp - Extra breathing room for heroic elements. */
    public val extraExtraLarge: Dp = 48.dp
)

/**
 * Fixed sizes for UI components like icons, avatars, and buttons.
 */
@Immutable
public data class AniFluxSizes(
    /** 16.dp - Small utility icons. */
    public val extraSmall: Dp = 16.dp,
    /** 24.dp - Standard navigation and action icons. */
    public val small: Dp = 24.dp,
    /** 32.dp - Prominent UI elements or small avatars. */
    public val medium: Dp = 32.dp,
    /** 48.dp - Large buttons or medium avatars. */
    public val large: Dp = 48.dp,
    /** 64.dp - Hero icons or large thumbnails. */
    public val extraLarge: Dp = 64.dp,
    /** 96.dp - Feature illustrations or profile pictures. */
    public val extraExtraLarge: Dp = 96.dp,
    /** 128.dp - Massive branding or decorative elements. */
    public val extraExtraExtraLarge: Dp = 128.dp
)