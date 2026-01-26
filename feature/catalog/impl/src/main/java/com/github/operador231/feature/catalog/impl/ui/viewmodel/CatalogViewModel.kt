package com.github.operador231.feature.catalog.impl.ui.viewmodel

import com.github.operador231.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class CatalogViewModel @Inject constructor() : BaseViewModel<CatalogUiState, CatalogUiEffect>(
    CatalogUiState(isLoading = true)
) {
}