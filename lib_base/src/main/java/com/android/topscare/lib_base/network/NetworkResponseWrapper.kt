package com.android.topscare.lib_base.network

import com.google.gson.annotations.SerializedName

class NetworkResponseWrapper<T>(
    @SerializedName("data") val data: T,
    @SerializedName("resultCode") val resultCode: Int,
    @SerializedName("msg") val message: String?
)

