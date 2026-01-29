package com.github.operador231.core.ui.utils

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

public val LocalWindowSizeClass: ProvidableCompositionLocal<WindowSizeClass> = staticCompositionLocalOf {
    error("No WindowSizeClass provided")
}