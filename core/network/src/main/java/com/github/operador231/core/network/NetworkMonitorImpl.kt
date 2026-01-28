package com.github.operador231.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.core.content.getSystemService
import com.github.operador231.core.domain.model.NetworkStatus
import com.github.operador231.core.domain.network.NetworkMonitor
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

/**
 * An implementation of [NetworkMonitor] that uses [ConnectivityManager] to observe network
 * status changes.
 *
 * @param ctx The application context, used to obtain the [ConnectivityManager] system service.
 */
public class NetworkMonitorImpl @Inject constructor(
    @param:ApplicationContext private val ctx: Context
) : NetworkMonitor {

    /**
     * Creates a [NetworkStatus] object based on the given [NetworkCapabilities].
     *
     * A network is considered online if it has both the [NetworkCapabilities.NET_CAPABILITY_INTERNET]
     * and [NetworkCapabilities.NET_CAPABILITY_VALIDATED] capabilities. It is considered metered
     * if it does *not* have the [NetworkCapabilities.NET_CAPABILITY_NOT_METERED] capability.
     *
     * @param caps The [NetworkCapabilities] of the current network, or null if unavailable.
     * @return A [NetworkStatus] instance representing the current network state.
     */
    private fun getStatus(caps: NetworkCapabilities?): NetworkStatus {
        return NetworkStatus(
            isOnline = caps?.let {
                it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                        it.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            } ?: false,
            isMetered = caps?.let {
                !it.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
            } ?: false
        )
    }

    override val status: Flow<NetworkStatus> = callbackFlow {
        val connectivityManager = ctx.getSystemService<ConnectivityManager>()

        val callback = object : ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                trySend(getStatus(connectivityManager?.getNetworkCapabilities(network)))
            }

            override fun onCapabilitiesChanged(network: Network, caps: NetworkCapabilities) {
                trySend(getStatus(caps))
            }

            override fun onLost(network: Network) {
                trySend(NetworkStatus(isOnline = false, isMetered = false))
            }
        }

        connectivityManager?.registerDefaultNetworkCallback(callback)

        trySend(getStatus(connectivityManager?.getNetworkCapabilities(connectivityManager.activeNetwork)))

        awaitClose {
            connectivityManager?.unregisterNetworkCallback(callback)
        }
    }.distinctUntilChanged()
}