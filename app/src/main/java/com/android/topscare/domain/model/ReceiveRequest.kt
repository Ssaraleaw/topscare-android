package com.android.topscare.domain.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ReceiveRequest(
    val id: String,
    @SerializedName("value") val amount: Int,
    @SerializedName("expdate")  val expDate: String,
    val lot: String,
    val name: String
)
