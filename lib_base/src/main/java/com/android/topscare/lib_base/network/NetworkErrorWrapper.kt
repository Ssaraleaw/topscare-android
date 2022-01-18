package com.android.topscare.lib_base.network

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class NetworkErrorWrapper(
    @SerializedName("errors") val errors: List<NetworkError>
)

@Keep
data class NetworkError(
    @SerializedName("status") val status: Int,
    @SerializedName("title") val title: String,
    @SerializedName("detail") val detail: String
)