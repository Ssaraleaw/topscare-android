package com.android.topscare.domain.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CheckIpRequest(
    @SerializedName("ip") val ip: String,
    @SerializedName("key") val key: String
)
