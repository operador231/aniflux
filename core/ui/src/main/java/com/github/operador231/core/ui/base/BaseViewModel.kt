package com.github.operador231.core.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * A base [ViewModel] that enforces Undirectional Data Flow (UDF).
 *
 * This class manages two primary communication streams between the UI and the ViewModel:
 * 1. **State ([UiState])**: Represents the persistent status of the UI.
 * 2. **Effect ([UiEffect])**: Represents one-off side effects (e.g. navigation, toasts).
 *
 * @param S The type representing the UI state.
 * @param F The type representing one-time UI effects.
 * @property initialState The starting state of the UI when the ViewModel is first created.
 * */
public abstract class BaseViewModel<S : UiState<Any>, F : UiEffect>(
    initialState: S
) : ViewModel() {

    /** Internal mutable state holder */
    private val _uiState = MutableStateFlow(initialState)

    /**
     * A [StateFlow] that emits the current UI state.
     * The UI should collect this to render its views.
     * */
    public val uiState: StateFlow<S> = _uiState.asStateFlow()

    /**
     * Internal channel for side-effects. Buffered to ensure effects are not lost
     * if the UI is temporarily not consuming them.
     * */
    private val _uiEffect = Channel<F>(Channel.BUFFERED)

    /**
     * A [Flow] of one-time events ([UiEffect]).
     * These should be collected by the UI (e.g. via LaunchedEffect in Compose)
     * */
    public val uiEffect: Flow<F> = _uiEffect.receiveAsFlow()

    /**
     * Updates the current [uiState] using the provided [reducer].
     * This ensures thread-safe atomic updates to the state.
     * */
    protected fun updateState(reducer: S.() -> S) {
        _uiState.update { reducer(it) }
    }

    /**
     * Dispatches a one-time [UiEffect] to the UI.
     * The effect is sent asynchronously within the [viewModelScope] to avoid blocking the UI.
     *
     * @param effect The effect to be triggered.
     * */
    protected fun sendEffect(effect: F) {
        viewModelScope.launch { _uiEffect.send(effect) }
    }
}