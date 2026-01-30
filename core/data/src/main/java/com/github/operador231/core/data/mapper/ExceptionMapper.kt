package com.github.operador231.core.data.mapper

import com.github.operador231.core.data.exceptions.AccessDeniedException
import com.github.operador231.core.data.exceptions.AppException
import com.github.operador231.core.data.exceptions.NotFoundException
import com.github.operador231.core.data.exceptions.OfflineException
import com.github.operador231.core.data.exceptions.ServerErrorException
import com.github.operador231.core.data.exceptions.UnauthorizedException
import com.github.operador231.core.data.exceptions.UnknownErrorException
import com.github.operador231.core.domain.model.Action
import com.github.operador231.core.domain.model.DomainError
import kotlinx.serialization.SerializationException
import okio.IOException
import retrofit2.HttpException
import timber.log.Timber
import java.net.SocketTimeoutException

public fun Throwable.toAppException(): AppException = when (this) {
    is AppException -> this
    is SocketTimeoutException -> ServerErrorException()
    is HttpException -> when (this.code()) {
        401 -> UnauthorizedException()
        403 -> AccessDeniedException()
        404 -> NotFoundException()
        504 -> OfflineException()
        in 500..599 -> ServerErrorException()
        else -> UnknownErrorException().also { Timber.e(this) }
    }
    is SerializationException -> UnknownErrorException().also { Timber.e(this) }
    is IOException -> OfflineException()
    else -> UnknownErrorException().also { Timber.e(this) }
}

public fun AppException.toDomain(): DomainError {
    Timber.e(this)

    return when (this) {
        is UnauthorizedException -> DomainError.ActionRequired(Action.Auth)
        is AccessDeniedException -> DomainError.AccessDenied
        is NotFoundException -> DomainError.NotFound
        is OfflineException -> DomainError.Offline
        is ServerErrorException -> DomainError.ServerUnavailable
        else -> DomainError.UnknownError
    }
}