package com.github.operador231.core.domain.model

/**
 * Represents a required user action to resolve a [DomainError.ActionRequired].
 *
 * This sealed interface defines specific actions that the user must perform
 * to proceed, such as authentication or solving a CAPTCHA.
 */
public sealed interface Action {

    /**
     * Indicates that the user needs to authenticate.
     * This action is required when an operation needs user credentials to proceed.
     */
    public data object Auth : Action

    /**
     * Indicates that the user needs to solve a CAPTCHA.
     * This is often required to prove the user is not a bot, typically after
     * multiple failed attempts or suspicious activity.
     */
    public data object Captcha : Action
}