package com.github.operador231.feature.catalog.impl.ui.viewmodel

import com.github.operador231.core.navigation.Route
import com.github.operador231.core.ui.base.UiEffect

internal data class CatalogUiEffect(
    val onNavigate: (Route)
): UiEffect