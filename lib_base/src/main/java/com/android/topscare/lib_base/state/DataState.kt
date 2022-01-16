package com.android.topscare.lib_base.state

import com.android.topscare.lib_base.base.Event
sealed class DataState<out T> {
    data class Success<T>(val data: T?) : DataState<T>()
    data class Error<T>(val data: T?, val exception: Event<Exception>) : DataState<T>()
    data class Loading<T>(val data: T?) : DataState<T>()
    data class Disable<T>(val data: T?) : DataState<T>()

    fun toData(): T? = when (this) {
        is Success -> this.data
        is Error -> this.data
        is Loading -> this.data
        is Disable -> this.data
    }

    fun isLoading(): Boolean? = if (this is Loading) true else null
    fun isSuccess(): Boolean? = if (this is Success) true else null
    fun isError(): Boolean? = if (this is Error) true else null
    fun toError(): Exception? = if (this is Error) this.exception.getContentIfNotHandled() else null
    fun toErrorMessage(): String? = if (this is Error) this.exception.getContentIfNotHandled()?.message else null
    fun isDisable(): Boolean? = if (this is Disable) true else null
}
