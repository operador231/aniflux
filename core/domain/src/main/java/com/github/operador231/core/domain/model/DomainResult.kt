package com.github.operador231.core.domain.model

/**
 * A functional wrapper for holding either a successful value or a domain-specific error.
 * Encourages explicit error handling across the architecture layers.
 *
 * @param T The type of the successful value.
 */
public sealed class DomainResult<out T> {

    /** Represents a successful operation.
     *
     * @property value The resulting data.
     * */
    public data class Ok<out T>(val value: T) : DomainResult<T>()

    /** Represents a failed operation.
     * @property error The specific [DomainError] type.
     * */
    public data class Err(val error: DomainError) : DomainResult<Nothing>()

    /**
     * Transforms the successful value if present, otherwise propagates the error.
     */
    public inline fun <R> map(transform: (T) -> R): DomainResult<R> =
        when (this) {
            is Ok -> Ok(transform(value))
            is Err -> this
        }

    /**
     * Executes the given action if the result is [Ok].
     */
    public inline fun onOk(action: (T) -> Unit): DomainResult<T> = apply {
        if (this is Ok) action(value)
    }

    /**
     * Executes the given action if the result is [Err].
     */
    public inline fun onErr(action: (DomainError) -> Unit): DomainResult<T> = apply {
        if (this is Err) action(error)
    }
}