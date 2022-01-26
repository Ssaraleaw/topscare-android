package com.android.topscare.lib_base.network

import android.content.res.Resources
import com.google.gson.Gson
import io.sentry.Sentry
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T> doRequest(apiCall: suspend () -> T): T? {
    return try {
        apiCall()
    } catch (throwable: Throwable) {
        null
//        when (throwable) {
//            is SocketTimeoutException -> throw NoNetworkException(throwable.message)
//            is UnknownHostException -> throw ServerUnreachableException(throwable.message)
//            is HttpException -> {
//                Sentry.captureException(throwable)
//                throw resolveHttpException(throwable)
//            }
//            else -> throw Exception(throwable.message)
//        }
    }
}

fun resolveHttpException(exception: HttpException): Exception {
    val errorWrapper = Gson().fromJson<NetworkErrorWrapper?>(
        exception.response()?.errorBody()?.string(),
        NetworkErrorWrapper::class.java
    )
    return when (exception.code()) {
        401 -> AuthorizationException(errorWrapper)
        403 -> ForbiddenException(errorWrapper)
        404 -> Resources.NotFoundException(errorWrapper.toString())
        422 -> UnProcessableEntityException(errorWrapper)
        400 -> BadRequestException(errorWrapper)
        429 -> TooManyRequestException(errorWrapper)
        else -> InternalServerException(exception.message())
    }
}
