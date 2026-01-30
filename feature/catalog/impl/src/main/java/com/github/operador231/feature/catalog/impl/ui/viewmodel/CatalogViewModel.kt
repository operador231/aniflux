package com.github.operador231.feature.catalog.impl.ui.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.github.operador231.core.data.mapper.toAppException
import com.github.operador231.core.data.mapper.toDomain
import com.github.operador231.core.domain.model.NetworkStatus
import com.github.operador231.core.domain.network.NetworkMonitor
import com.github.operador231.core.ui.base.BaseViewModel
import com.github.operador231.feature.catalog.impl.data.usecase.GetAnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class CatalogViewModel @Inject constructor(
    getAnimeUseCase: GetAnimeUseCase,
    networkMonitor: NetworkMonitor
) : BaseViewModel<CatalogUiState, CatalogUiEffect>(
    CatalogUiState(isLoading = true)
) {

    val anime = getAnimeUseCase().cachedIn(viewModelScope)

    val networkStatus = networkMonitor.status.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = NetworkStatus(isOnline = true, isMetered = false)
    )

    internal fun onRetry() {
        sendEffect(effect = CatalogUiEffect.OnRetry)
    }

    internal fun onError(error: Throwable) {
        Timber.i(error)
        sendEffect(
            effect = CatalogUiEffect.OnError(
                error = error.toAppException().toDomain()
            )
        )
    }
}