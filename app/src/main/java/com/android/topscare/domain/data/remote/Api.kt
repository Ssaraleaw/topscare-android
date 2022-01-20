package com.android.topscare.domain.data.remote

import com.android.topscare.domain.model.CountRequest
import com.android.topscare.domain.model.OrderRequest
import com.android.topscare.domain.model.ProductResponse
import com.android.topscare.domain.model.ReceiveRequest
import com.android.topscare.lib_base.network.NetworkResponseWrapper
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {
    @GET("topscare/index.php/product/search")
    suspend fun searchProduct(@Query("key") key : String): NetworkResponseWrapper<ProductResponse?>

    @POST("topscare/index.php/product/count")
    suspend fun addCountProduct(@Body countRequest : CountRequest): NetworkResponseWrapper<Any>

    @POST("topscare/index.php/product/order")
    suspend fun insertOrderProduct(@Body orderRequest : OrderRequest): NetworkResponseWrapper<Any>

    @POST("topscare/index.php/product/receive")
    suspend fun insertReceiveProduct(@Body receiveRequest : ReceiveRequest): NetworkResponseWrapper<Any>
}