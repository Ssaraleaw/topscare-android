package com.android.topscare.domain.model

import androidx.annotation.Keep

@Keep
data class ProductHistoryRequest(
    val key: String?,
    val page: Int,
    val limit: Int
)
