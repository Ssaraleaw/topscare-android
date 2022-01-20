package com.android.topscare.domain.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 {
    "id":"00020",
    "value": 20,
    "free": 10,
    "name": "HHname1"
 }
 */
@Keep
data class OrderRequest(
    val id: String,
    @SerializedName("value") val amount: Int,
    val free: Int,
    val name: String
)
