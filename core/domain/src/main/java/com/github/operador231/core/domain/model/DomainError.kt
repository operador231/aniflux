package com.github.operador231.core.domain.model

/**
 * Defines a set of categorized errors that can occur within the domain layer.
 * These are abstracted from specific framework or network exceptions to be UI-agnostic.
 */
public sealed class DomainError(public open val message: String? = null) {

    /**
     * A domain error that requires the user to take an action to resolve.
     *
     * @param action The action that the user needs to take to resolve the error.
     * */
    public data class ActionRequired(val action: Action) : DomainError()

    /**
     * User does not have permission to access the resource and authentication will change nothing.
     * */
    public object AccessDenied : DomainError()

    /** The requested resource was not found.*/
    public object NotFound : DomainError()

    /**
     * The server is currently unavailable.
     * */
    public object ServerUnavailable : DomainError()

    /**
     * The device is offline or not connected to the internet.
     * */
    public object Offline : DomainError()

    /**
     * An unclassified error occurred.
     * */
    public object UnknownError : DomainError()
}