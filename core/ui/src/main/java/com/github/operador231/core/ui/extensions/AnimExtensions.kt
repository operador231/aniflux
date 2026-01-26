package com.github.operador231.core.ui.extensions

import androidx.compose.runtime.Composable
import com.airbnb.lottie.compose.LottieCompositionResult
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.github.operador231.core.ui.R
import com.github.operador231.core.ui.model.Anim

/**
 * A [getSpec] extension function that maps [Anim] to a lottie animation.
 *
 * @return The corresponding [LottieCompositionSpec] for the given [Anim].
 * */
@Composable
public fun Anim.getSpec(): LottieCompositionSpec.RawRes = when (this) {
    Anim.ANIM_ERROR -> LottieCompositionSpec.RawRes(R.raw.anim_error)
}