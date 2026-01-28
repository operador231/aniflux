package com.github.operador231.core.network.model

import androidx.annotation.Keep

/**
 * A wrapper class for a network result for type [T]. If the network request is successful, the
 * response will be a [Ok] containing the data. If the network request is a failure, the
 * response will be a [Err] containing the [Throwable].
 */
@Keep
public sealed class NetworkResult<out T> {
    /**
     * A successful network result with the relevant [T] data.
     */
    public data class Ok<T>(val value: T) : NetworkResult<T>()

    /**
     * A failed network result with the relevant [throwable] error.
     */
    public data class Err(val throwable: Throwable) : NetworkResult<Nothing>()
}