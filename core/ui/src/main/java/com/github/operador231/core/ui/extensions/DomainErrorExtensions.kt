package com.github.operador231.core.ui.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.github.operador231.core.domain.model.Action
import com.github.operador231.core.domain.model.DomainError
import com.github.operador231.core.ui.R
import com.github.operador231.core.ui.model.Anim

/**
 * Extension function to convert a [DomainError] into a human-readable error message.
 *
 * @return A string representing the error message.
 * */
@Composable
public fun DomainError.getErrorMessage(): String = when (this) {
    is DomainError.Offline -> stringResource(R.string.st_no_network_connection_err)
    is DomainError.NotFound -> stringResource(R.string.st_msg_nothing_found)
    is DomainError.ServerUnavailable -> stringResource(R.string.st_msg_server_unavailable)
    is DomainError.AccessDenied -> stringResource(R.string.st_msg_access_denied)
    is DomainError.ActionRequired -> {
        if (this.action is Action.Auth) stringResource(R.string.st_msg_auth_required)
        else stringResource(R.string.st_msg_unknown_error)
    }
    else -> stringResource(R.string.st_msg_unknown_error)
}

/**
 * Extension function to convert a [DomainError] into a corresponding [Anim].
 *
 * @return The corresponding [Anim] for the given [DomainError].
 * */
public fun DomainError.getErrorAnim(): Anim = when (this) {
    is DomainError.NotFound -> Anim.ANIM_NOT_FOUND
    else -> Anim.ANIM_ERROR
}