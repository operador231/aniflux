package com.github.operador231.feature.catalog.impl.ui.viewmodel

import com.github.operador231.core.domain.model.DomainError
import com.github.operador231.core.domain.model.MediaId
import com.github.operador231.core.ui.base.UiEffect

internal sealed interface CatalogUiEffect: UiEffect {
    data class OnNavigate(val id: MediaId): CatalogUiEffect
    data class OnError(val error: DomainError): CatalogUiEffect
    data object OnRetry: CatalogUiEffect
}