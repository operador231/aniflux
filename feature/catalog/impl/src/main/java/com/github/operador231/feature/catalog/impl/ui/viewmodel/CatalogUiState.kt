package com.github.operador231.feature.catalog.impl.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.github.operador231.core.ui.base.UiState

@Immutable
internal data class CatalogUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false
): UiState<Nothing>
