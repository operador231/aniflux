package com.github.operador231.core.domain.model

/**
 * Represents the current network connectivity status.
 *
 * This data class provides a simple snapshot of the network state,
 * indicating whether the device is online and if the connection is metered.
 *
 * @property isOnline True if the device has an active internet connection, false otherwise.
 * @property isMetered True if the active network connection is metered (e.g., cellular data),
 *                     false otherwise (e.g., Wi-Fi).
 */
public data class NetworkStatus(
    val isOnline: Boolean,
    val isMetered: Boolean
)
