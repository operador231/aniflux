package com.github.operador231.core.data.exceptions

public class UnauthorizedException(
    message: String? = null,
    cause: Throwable? = null
): AppException(message, cause)