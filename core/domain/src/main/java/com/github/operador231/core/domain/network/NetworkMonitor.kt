package com.github.operador231.core.domain.network

import com.github.operador231.core.domain.model.NetworkStatus
import kotlinx.coroutines.flow.Flow

/**
 * An interface for monitoring network connectivity changes.
 *
 * Implementations of this interface provide a reactive way to observe the
 * network status.
 */
public interface NetworkMonitor {

    /**
     * A [Flow] that emits the current [NetworkStatus] and subsequent updates
     * as network connectivity changes.
     */
    public val status: Flow<NetworkStatus>
}