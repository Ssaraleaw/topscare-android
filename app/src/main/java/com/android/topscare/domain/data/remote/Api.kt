package com.android.topscare.domain.data.remote

import com.android.topscare.domain.model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("topscare/index.php/product/search")
    suspend fun searchProduct(@Query("key") key : String): ProductResponse
}