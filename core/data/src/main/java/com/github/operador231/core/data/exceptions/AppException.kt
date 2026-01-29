package com.github.operador231.core.data.exceptions

/**
 * Base class for all application-level exceptions.
 * */
public abstract class AppException(
    message: String? = null,
    cause: Throwable? = null
): Exception(message, cause)