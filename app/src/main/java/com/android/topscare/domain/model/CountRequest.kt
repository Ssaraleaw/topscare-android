package com.android.topscare.domain.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CountRequest(
    val id: String,
    @SerializedName("value") val amount: Int,
    val name: String,
)
