package com.github.operador231.core.data.exceptions

public class ServerErrorException(
    message: String? = null,
    cause: Throwable? = null
): AppException(message, cause)