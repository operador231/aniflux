package com.github.operador231.core.ui.model

import com.airbnb.lottie.compose.LottieCompositionSpec
import com.github.operador231.core.ui.R

/**
 * Represents predefined Lottie animations used within the application.
 *
 * This enum provides a type-safe way to reference Lottie animations from raw resources.
 * Each entry holds a [LottieCompositionSpec.RawRes] which can be directly used
 * with the Lottie compose component.
 *
 * @property spec The [LottieCompositionSpec.RawRes] pointing to the animation's JSON file in the raw resources.
 */
public enum class Anim(public val spec: LottieCompositionSpec.RawRes) {
    ANIM_ERROR(spec = LottieCompositionSpec.RawRes(R.raw.anim_error)),
    ANIM_NOT_FOUND(spec = LottieCompositionSpec.RawRes(R.raw.anim_not_found))
}