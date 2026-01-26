package com.github.operador231.core.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.operador231.core.ui.annotations.ExperimentalAniFluxUi
import com.github.operador231.core.ui.theme.AniFluxTheme

/**
 * Contains the default values used by [MediaBadge].
 */
public object MediaBadgeDefaults {
    /** The default background color. */
    public val containerColor: Color
        @Composable get() = MaterialTheme.colorScheme.primary

    /** The default content (text) color. */
    public val contentColor: Color = Color.White

    /** The vertical padding applied to the text within the badge container. */
    public val contentVerticalPadding: Dp = 2.dp

    /** The horizontal padding applied to the text within the badge container. */
    public val contentHorizontalPadding: Dp = 6.dp

    public val paddingValues: PaddingValues = PaddingValues(6.dp)
}

/**
 * A compact, high-contrast status indicator used to display metadata such as
 * numerical ratings, age classifications, or content tags.
 *
 * This component acts as a high-visibility overlay, designed to be placed
 * atop rich media (like posters).
 *
 * @param modifier The modifier to be applied to the root container.
 * @param text The string to display.
 * @param color The background color of the surface.
 * @param contentColor The content color of the text.
 */
@ExperimentalAniFluxUi
@Composable
public fun MediaBadge(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MediaBadgeDefaults.containerColor,
    contentColor: Color = MediaBadgeDefaults.contentColor,
) {
    Surface(
        color = color,
        contentColor = contentColor,
        shape = CircleShape,
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmallEmphasized,
            modifier = Modifier.padding(
                horizontal = MediaBadgeDefaults.contentHorizontalPadding,
                vertical = MediaBadgeDefaults.contentVerticalPadding
            )
        )
    }
}

@OptIn(ExperimentalAniFluxUi::class)
@Preview(showBackground = true)
@Composable
private fun RatingBadgePreview() {
    AniFluxTheme {
        MediaBadge(text = "PG-13")
    }
}