package com.github.operador231.core.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import coil3.compose.AsyncImage
import com.github.operador231.core.ui.annotations.ExperimentalAniFluxUi
import com.github.operador231.core.ui.extensions.debugBorder
import com.github.operador231.core.ui.theme.AniFluxTheme

@ExperimentalAniFluxUi
@Composable
public fun MediaPoster(
    url: String?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    MediaPosterContent(
        url = url,
        description = contentDescription,
        modifier = modifier
    )
}

@ExperimentalAniFluxUi
@Composable
public fun MediaPoster(
    url: String?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    badgeLabel: String,
    badgeContainerColor: Color,
    badgeContentColor: Color = MediaBadgeDefaults.contentColor
) {
    Box(modifier = modifier) {
        MediaPosterContent(
            url = url,
            description = contentDescription,
            modifier = Modifier.debugBorder()
        )
        MediaBadge(
            text = badgeLabel,
            color = badgeContainerColor,
            contentColor = badgeContentColor,
            modifier = Modifier.padding(MediaBadgeDefaults.paddingValues)
        )
    }
}

@Composable
internal fun MediaPosterContent(
    url: String?,
    description: String?,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = url,
        contentDescription = description,
        contentScale = ContentScale.Crop,
        modifier = Modifier.debugBorder()
    )
}

@Composable
@PreviewScreenSizes
private fun MediaPosterContentPreview() {
    AniFluxTheme {
        MediaPosterContent(
            url = null,
            description = null
        )
    }
}