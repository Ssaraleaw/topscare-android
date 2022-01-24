package com.android.topscare.domain.data.remote

import com.android.topscare.domain.model.*
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

    @GET("topscare/index.php/product/listCount")
    suspend fun searchProductCount(@Query("limit") limit : Int,
                                   @Query("page") page : Int,
                                   @Query("key") key : String?
    ): NetworkResponseWrapper<List<CountResponse>?>

    @POST("topscare/index.php/product/deleteCount")
    suspend fun deleteCountProduct(@Body deleteRequest : DeleteRequest): NetworkResponseWrapper<Any>

    @GET("topscare/index.php/product/listOrder")
    suspend fun searchProductOrder(@Query("limit") limit : Int,
                                   @Query("page") page : Int,
                                   @Query("key") key : String?
    ): NetworkResponseWrapper<List<OrderHistoryResponse>?>

    @POST("topscare/index.php/product/deleteOrder")
    suspend fun deleteOrderProduct(@Body deleteRequest : DeleteRequest): NetworkResponseWrapper<Any>
}