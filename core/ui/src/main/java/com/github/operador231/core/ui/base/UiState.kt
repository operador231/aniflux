package com.github.operador231.core.ui.base

/**
 * Marker interface for the UI state of a screen.
 * */
public interface UiState<out T> {
    public data object Loading : UiState<Nothing>
    public data object Error : UiState<Nothing>
    public data class Success<T>(val data: T) : UiState<T>
}