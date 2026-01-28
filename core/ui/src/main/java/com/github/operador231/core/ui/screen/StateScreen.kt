package com.github.operador231.core.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty
import com.github.operador231.core.domain.model.DomainError
import com.github.operador231.core.ui.R
import com.github.operador231.core.ui.extensions.getErrorAnim
import com.github.operador231.core.ui.extensions.getErrorMessage
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
 * For more details see [Anim]
 * @param text The text to be displayed below the animation.
 * @param action Optional action (e.g. retry) to be displayed below the message.
 * */
@Composable
public fun StateScreen(
    modifier: Modifier = Modifier,
    anim: Anim? = null,
    text: String = stringResource(R.string.st_no_features_found),
    action: (@Composable ColumnScope.() -> Unit)? = null,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        StubContent(
            anim = anim,
            text = text,
            action = action
        )
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
 *
 * */
@Composable
public fun StateScreen(
    modifier: Modifier = Modifier,
    content: (@Composable BoxScope.() -> Unit)
) {
    Box(modifier = modifier.fillMaxSize()) {
        content.invoke(this)
    }
}

@Composable
private fun StubContent(
    anim: Anim? = Anim.ANIM_ERROR,
    text: String,
    action: (@Composable ColumnScope.() -> Unit)? = null,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(AniFluxTheme.paddings.large)
    ) {
        anim?.let {
            val composition by rememberLottieComposition(spec = it.spec)
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier.size(AniFluxTheme.sizes.extraExtraExtraLarge)
            )
        }

        Text(
            text = text,
            style = MaterialTheme.typography.titleMediumEmphasized,
            color = MaterialTheme.colorScheme.outline,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = AniFluxTheme.paddings.extraLarge)
        )

        action?.invoke(this)
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

/**
 * A top-level state-aware component that renders an appropriate error screen
 * based on the provided [DomainError].
 *
 * This component acts as a central hub for error handling across the application,
 * ensuring a consistent user experience when something goes wrong.
 *
 * > **Note**: Do not use this component for [DomainError.ActionRequired] because it cannot be handled.
 * > Use [StateScreen] with custom `content` instead.
 *
 * @param modifier The [Modifier] to be applied to the container.
 * @param error The specific [DomainError] instance representing the failure state.
 * @param enableAnim A flag indicating whether to enable the animation in the error screen.
 * @param onAction An optional callback triggered by the action button (e.g., "Retry").
 * If null, the action button will be hidden.
 *
 */
@Composable
public fun ErrorContent(
    modifier: Modifier = Modifier,
    error: DomainError,
    enableAnim: Boolean = true,
    onAction: (() -> Unit)? = null
) {
    val useDynamicProps: Boolean = when (error) {
        is DomainError.NotFound -> true
        else -> false
    }

    val dynamicProps = if (useDynamicProps) rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primary.toArgb(),
            keyPath = arrayOf("**")
        )
    ) else null

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (enableAnim) {
            val composition by rememberLottieComposition(spec = error.getErrorAnim().spec)
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                dynamicProperties = dynamicProps,
                modifier = Modifier.size(AniFluxTheme.sizes.extraExtraExtraLarge)
            )
        }

        Text(
            text = error.getErrorMessage(),
            style = MaterialTheme.typography.titleMediumEmphasized,
            color = MaterialTheme.colorScheme.outline,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = AniFluxTheme.paddings.extraLarge)
        )

        Spacer(modifier = Modifier.height(AniFluxTheme.paddings.large))

        onAction?.let {
            Button(
                onClick = it
            ) {
                Text(
                    text = stringResource(R.string.st_retry),
                    style = MaterialTheme.typography.titleMediumEmphasized,
                )
            }
        }
    }
}

@Composable
@PreviewScreenSizes
private fun ErrorContentPreview() {
    AniFluxTheme {
        ErrorContent(error = DomainError.NotFound)
    }
}