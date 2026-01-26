package com.github.operador231.core.ui.extensions

import androidx.compose.foundation.border
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * A flag that determines whether debug modifiers should be enabled or not.
 *
 * When set to true, debug modifiers will be applied to composables.
 *
 * Should only be used for development purposes.
 * */
public const val debugModifiersEnabled: Boolean = false

/**
 * [debugBorder] is a modifier that applies a border to the composable it is applied to.
 * It is only applied when [debugModifiersEnabled] is true.
 *
 * @param color The color of the border.
 * */
public fun Modifier.debugBorder(
    color: Color = Color.Red
): Modifier {
    return if (debugModifiersEnabled) {
        this.border(
            width = 1.dp,
            color = color
        )
    }
    else this
}