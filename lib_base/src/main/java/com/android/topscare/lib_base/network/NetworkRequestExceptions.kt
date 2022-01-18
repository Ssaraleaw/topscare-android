package com.android.topscare.lib_base.network

class ServerUnreachableException(message: String?) : Exception(message)
class NoNetworkException(message: String?) : Exception(message)
class AuthorizationException(override val errorWrapper: NetworkErrorWrapper?) :
    BaseNetworkException()
class ForbiddenException(override val errorWrapper: NetworkErrorWrapper?) : BaseNetworkException()
class NotFoundException(override val errorWrapper: NetworkErrorWrapper?) : BaseNetworkException()
class InternalServerException(message: String?) : Exception()
class BadRequestException(override val errorWrapper: NetworkErrorWrapper?) : BaseNetworkException()
class UnProcessableEntityException(override val errorWrapper: NetworkErrorWrapper?) :
    BaseNetworkException()

class TooManyRequestException(override val errorWrapper: NetworkErrorWrapper?) :
    BaseNetworkException()

abstract class BaseNetworkException : Exception() {
    abstract val errorWrapper: NetworkErrorWrapper?
    fun getFirstError(): NetworkError? = errorWrapper?.errors?.firstOrNull()
}