package com.github.operador231.feature.catalog.impl.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.operador231.core.domain.model.AnimePreview
import com.github.operador231.core.domain.model.Media
import com.github.operador231.core.domain.model.MediaId
import com.github.operador231.core.ui.ScoreConstants
import com.github.operador231.core.ui.annotations.ExperimentalAniFluxUi
import com.github.operador231.core.ui.component.MediaPoster
import com.github.operador231.core.ui.extensions.getLabel
import com.github.operador231.core.ui.model.Season
import com.github.operador231.core.ui.theme.AniFluxTheme
import com.github.operador231.core.ui.theme.Typography
import com.github.operador231.core.ui.theme.scoreBadColor
import com.github.operador231.core.ui.theme.scoreExcellentColor
import com.github.operador231.core.ui.theme.scoreGoodColor
import com.github.operador231.core.ui.theme.scoreNormalColor
import com.github.operador231.core.ui.theme.scoreWorstColor
import com.github.operador231.feature.catalog.impl.R
import kotlinx.collections.immutable.persistentListOf

/**
 * A list of supported [Media] implementations that can be used with [PreviewListItem].
 * */
private val supportedMediaImplementations = persistentListOf(
    AnimePreview::class
)

public object PreviewDefaults {
    public val PosterWidth: Dp = 150.dp
}

/**
 * A list item component that acts as a dispatcher for different [Media] types.
 *
 * @param item The data entity to be displayed (e.g., [AnimePreview]).
 * @param modifier The [Modifier] to be applied to the item container.
 * @param onClick Callback triggered with the entity's unique ID when the item is clicked.
 *
 * @throws IllegalArgumentException If the provided [item] type is not supported.
 * A full list of supported types you can see in [supportedMediaImplementations].
 */
@ExperimentalAniFluxUi
@Composable
public fun PreviewListItem(
    item: Media,
    modifier: Modifier = Modifier,
    onClick: ((MediaId) -> Unit)? = null
) {
    if (item::class !in supportedMediaImplementations)
        throw IllegalArgumentException(
            "Unsupported type: ${item::class}. " +
            "Supported types: $supportedMediaImplementations"
        )

    when (item) {
        is AnimePreview -> AnimePreviewListItem(item, modifier, onClick)
    }
}

/**
 * Default values for [AnimePreviewListItem] component.
 * */
internal object AnimeListItemDefaults {
    val height = 200.dp
    val posterWidth = 112.dp
}

/**
 * Returns a [Color] representing the visual "heat" of a score.
 * * The mapping follows [ScoreConstants] thresholds. If the score is below
 * the [ScoreConstants.WORST] threshold or is an invalid value, it defaults to [Color.Gray].
 *
 * @receiver The average score, typically on a scale of 0.0 to 10.0.
 */
private fun Float.getAvgColor(): Color = when {
    this >= ScoreConstants.EXCELLENT -> scoreExcellentColor
    this >= ScoreConstants.GOOD -> scoreGoodColor
    this >= ScoreConstants.NORMAL -> scoreNormalColor
    this >= ScoreConstants.BAD -> scoreBadColor
    this >= ScoreConstants.WORST -> scoreWorstColor
    else -> Color.Gray
}

/**
 * A full-width list item component that displays essential information about an anime.
 *
 * @param item The [AnimePreview] data entity to display.
 * @param modifier The modifier to be applied to the root container.
 * @param onClick A callback invoked when the user clicks on the list item.
 */
@ExperimentalAniFluxUi
@Composable
internal fun AnimePreviewListItem(
    item: AnimePreview,
    modifier: Modifier = Modifier,
    onClick: ((MediaId) -> Unit)?
) {
    Surface(
        modifier = modifier
            .fillMaxWidth(),
        onClick = { onClick?.invoke(item.id) },
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface,
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(AniFluxTheme.paddings.small)
        ) {
            MediaPoster(
                url = item.posterUrl,
                contentDescription = null,
                modifier = Modifier
                    .width(AnimeListItemDefaults.posterWidth)
                    .aspectRatio(0.7f)
                    //.height(AnimeListItemDefaults.height)
                    .clip(MaterialTheme.shapes.medium),
                badgeLabel = item.score.toString(),
                badgeContainerColor = item.score.getAvgColor()
            )

            AnimeMeta(item, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
internal fun AnimeMeta(
    item: AnimePreview,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(AniFluxTheme.paddings.extraSmall)
    ) {
        Text(
            text = item.title,
            style = Typography.titleMediumEmphasized,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        val genresLabel = remember {
            item.genres.joinToString(", ") { it.name }.trim()
        }
        if (genresLabel.isNotEmpty()) {
            Text(
                text = genresLabel,
                style = Typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        val ctx = LocalContext.current
        val metaLabel = remember(item, ctx) {
            val rating = item.rating.getLabel(ctx)
            val kind = item.type.getLabel(ctx)
            val status = item.status.getLabel(ctx)
            val season = "${Season.getSeasonByMonth(item.airMonth).getLabel(ctx)} ${item.airYear}"
            listOfNotNull(kind, season, status, rating)
                .filter { it.isNotBlank() }
                .joinToString(" • ")
        }
        Text(
            text =  metaLabel,
            style = Typography.labelSmall,
            color = MaterialTheme.colorScheme.outline,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        val description = item.description.ifBlank { stringResource(R.string.st_no_description) }
        Text(
            text = description,
            style = Typography.bodySmall,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
@ExperimentalAniFluxUi
public fun PreviewGridItem(
    item: Media,
    modifier: Modifier = Modifier,
    onClick: ((MediaId) -> Unit)? = null
) {
    if (item::class !in supportedMediaImplementations)
        throw IllegalArgumentException(
            "Unsupported type: ${item::class}. " +
                    "Supported types: $supportedMediaImplementations"
        )

    when (item) {
        is AnimePreview -> AnimeGridItem(item, modifier, onClick)
    }
}

@ExperimentalAniFluxUi
@Composable
internal fun AnimeGridItem(
    item: AnimePreview,
    modifier: Modifier = Modifier,
    onClick: ((MediaId) -> Unit)?
) {
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .aspectRatio(0.7f)
            .clickable { onClick?.invoke(item.id) }
    ) {
        MediaPoster(
            url = item.posterUrl,
            contentDescription = null,
            badgeLabel = item.score.toString(),
            badgeContainerColor = item.score.getAvgColor(),
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0.4f to Color.Transparent,
                        1f to Color.Black.copy(alpha = 0.9f)
                    )
                )
        )

        Text(
            text = item.title,
            style = Typography.labelMediumEmphasized,
            color = Color.White,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(AniFluxTheme.paddings.small)
        )
    }
}