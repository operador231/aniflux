package com.github.operador231.core.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.github.operador231.core.ui.annotations.ExperimentalAniFluxUi
import com.github.operador231.core.ui.extensions.debugBorder
import com.github.operador231.core.ui.extensions.shimmerEffect
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
        )
        MediaBadge(
            text = badgeLabel,
            color = badgeContainerColor,
            contentColor = badgeContentColor,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(MediaBadgeDefaults.paddingValues)
        )
    }
}

@Composable
internal fun MediaPosterContent(
    url: String?,
    description: String?,
    modifier: Modifier = Modifier
) {
    var isImageLoading by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.shimmerEffect(isImageLoading)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .crossfade(true)
                .build(),
            contentDescription = description,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .debugBorder(),
            onState = { state ->
                isImageLoading = state is AsyncImagePainter.State.Loading
            }
        )
    }
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