package com.github.operador231.core.data.exceptions

public class OfflineException(
    message: String? = null,
    cause: Throwable? = null
): AppException(message, cause)