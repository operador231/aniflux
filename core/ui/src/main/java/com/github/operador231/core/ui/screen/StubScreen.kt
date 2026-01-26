package com.github.operador231.core.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.github.operador231.core.ui.R
import com.github.operador231.core.ui.extensions.getSpec
import com.github.operador231.core.ui.model.Anim
import com.github.operador231.core.ui.theme.AniFluxTheme

/**
 * An universal fallback and state display screen.
 *
 * Used as a placeholder when feature modules are missing, content is unavailable
 * or during system-level states.
 *
 * @param modifier The [Modifier] to be applied to the root container.
 * @param anim Optional lottie animation to be displayer above the message.
 * For more details see [com.github.operador231.core.ui.model.Anim]
 * @param text The text to be displayed below the animation.
 * @param action Optional action (e.g. retry) to be displayed below the message.
 * */
@Composable
public fun StubScreen(
    modifier: Modifier = Modifier,
    anim: Anim? = null,
    text: String = stringResource(R.string.st_no_features_found),
    action: (@Composable ColumnScope.() -> Unit)? = null,
) {
    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { isVisible = true }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + expandHorizontally(),
        exit = fadeOut() + shrinkHorizontally()
    ) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            StubContent(text = text)
        }
    }
}

/**
 * An universal fallback and state display screen.
 *
 * Used as a placeholder when feature modules are missing, content is unavailable
 * or during system-level states.
 *
 * @param modifier The [Modifier] to be applied to the root container.
 * @param content Slot for custom content.
 * */
@Composable
public fun StubScreen(
    modifier: Modifier = Modifier,
    content: (@Composable BoxScope.() -> Unit)
) {
    Box(modifier = modifier.fillMaxSize()) {
        content()
    }
}

@Composable
private fun StubContent(
    anim: Anim? = Anim.ANIM_ERROR,
    text: String,
    action: (() -> Unit)? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(AniFluxTheme.paddings.large)
    ) {
        anim?.let {
            val composition by rememberLottieComposition(spec = it.getSpec())
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier.size(AniFluxTheme.sizes.extraExtraExtraLarge)
            )
        }

        Text(
            text = text,
            style = MaterialTheme.typography.titleMediumEmphasized,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = AniFluxTheme.paddings.extraLarge)
        )

        action?.let { it() }
    }
}

@Composable
@PreviewScreenSizes
private fun StubContentPreview() {
    AniFluxTheme {
        StubContent(
            anim = Anim.ANIM_ERROR,
            text = stringResource(R.string.st_no_features_found)
        )
    }
}