package com.github.operador231.feature.catalog.impl.ui.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.cachedIn
import com.github.operador231.core.data.mapper.toAppException
import com.github.operador231.core.data.mapper.toDomain
import com.github.operador231.core.ui.base.BaseViewModel
import com.github.operador231.core.ui.extensions.getErrorMessage
import com.github.operador231.core.ui.extensions.getErrorMessageResId
import com.github.operador231.feature.catalog.impl.data.usecase.GetAnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class CatalogViewModel @Inject constructor(
    getAnimeUseCase: GetAnimeUseCase
) : BaseViewModel<CatalogUiState, CatalogUiEffect>(
    CatalogUiState(isLoading = true)
) {

    val anime = getAnimeUseCase().cachedIn(viewModelScope)

    internal fun onError(error: Throwable) {
        Timber.e(error)
        sendEffect(
            effect = CatalogUiEffect.OnError(
                error = error.toAppException().toDomain()
            )
        )
    }
}