package com.github.operador231.core.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.github.operador231.core.domain.model.NetworkStatus
import com.github.operador231.core.ui.R
import com.github.operador231.core.ui.theme.onlineColor
import kotlinx.coroutines.delay

/**
 * A adaptive network status indicator that notifies about internet connection changes.
 *
 * The component automatically manages its visibility based on the provided [NetworkStatus]:
 * - Displays a sticky gray bar when the device is offline.
 * - Displays a green success bar for 3 seconds when the connection is restored, then hides itself.
 *
 * @param modifier The [Modifier] to be applied to the indicator container.
 * @param networkStatus The current connectivity state (usually collected from a NetworkMonitor).
 * @param onRestoreConnection Optional callback triggered exactly once when the status transitions
 * from offline to online. Use this to retry failed network requests.
 */
@Composable
public fun ConnectivityIndicator(
    modifier: Modifier = Modifier,
    networkStatus: NetworkStatus,
    onRestoreConnection: (() -> Unit)? = null,
) {
    var showStatus by remember { mutableStateOf(false) }
    LaunchedEffect(networkStatus) {
        if (!networkStatus.isOnline) {
            showStatus = true
        } else {
            onRestoreConnection?.invoke()
            delay(3000)
            showStatus = false
        }
    }

    val color = if (!networkStatus.isOnline) Color.Gray else onlineColor
    val textRes = if (!networkStatus.isOnline) R.string.st_offline else R.string.st_network_connection_restored

    AnimatedVisibility(
        visible = showStatus,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut()
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(color),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(textRes),
                style = MaterialTheme.typography.labelSmallEmphasized,
                color = Color.White
            )
        }
    }
}