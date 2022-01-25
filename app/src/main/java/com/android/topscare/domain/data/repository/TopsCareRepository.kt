package com.android.topscare.domain.data.repository

import com.android.topscare.domain.data.remote.Api
import com.android.topscare.domain.model.*
import com.android.topscare.domain.usecase.AppSettingUseCase
import com.android.topscare.lib_base.di.module.BaseOkHttpClient
import com.android.topscare.lib_base.network.NetworkResponseWrapper
import com.android.topscare.lib_base.network.doRequest
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Inject
import javax.inject.Named

class TopsCareRepository @Inject constructor(
    private val gson: GsonConverterFactory,
    @BaseOkHttpClient private val okHttpClient: OkHttpClient,
    private val appSettingUseCase: AppSettingUseCase
){
    var retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(gson)
    .baseUrl(appSettingUseCase.getBaseUrl())
    .client(okHttpClient)
    .build()

    fun update(){
        retrofit = Retrofit.Builder()
            .addConverterFactory(gson)
            .baseUrl(appSettingUseCase.getBaseUrl())
            .client(okHttpClient)
            .build()
    }

    suspend fun getProductByKey(key: String): ProductResponse?{
        val api = retrofit.create<Api>()
        return doRequest { api.searchProduct(key = key).data }
    }
    suspend fun getProductCount(limit: Int, page: Int, key: String?): List<CountResponse>?{
        val api = retrofit.create<Api>()
        return doRequest { api.searchProductCount(limit, page, key).data }
    }
    suspend fun getProductOrder(limit: Int, page: Int, key: String?): List<OrderHistoryResponse>?{
        val api = retrofit.create<Api>()
        return doRequest { api.searchProductOrder(limit, page, key).data }
    }
    suspend fun getProductReceive(limit: Int, page: Int, key: String?): List<ReceiveHistoryResponse>?{
        val api = retrofit.create<Api>()
        return doRequest { api.searchProductReceive(limit, page, key).data }
    }

    suspend fun insertCountProduct(request: CountRequest): NetworkResponseWrapper<Any>{
        val api = retrofit.create<Api>()
        return doRequest { api.addCountProduct(request) }
    }
    suspend fun deleteCountProduct(request: DeleteRequest): NetworkResponseWrapper<Any>{
        val api = retrofit.create<Api>()
        return doRequest { api.deleteCountProduct(request) }
    }
    suspend fun deleteOrderProduct(request: DeleteRequest): NetworkResponseWrapper<Any>{
        val api = retrofit.create<Api>()
        return doRequest { api.deleteOrderProduct(request) }
    }
    suspend fun deleteReceiveProduct(request: DeleteRequest): NetworkResponseWrapper<Any>{
        val api = retrofit.create<Api>()
        return doRequest { api.deleteReceiveProduct(request) }
    }
    suspend fun insertOrderProduct(request: OrderRequest): NetworkResponseWrapper<Any>{
        val api = retrofit.create<Api>()
        return doRequest { api.insertOrderProduct(request) }
    }
    suspend fun insertReceiveProduct(request: ReceiveRequest): NetworkResponseWrapper<Any>{
        val api = retrofit.create<Api>()
        return doRequest { api.insertReceiveProduct(request) }
    }
    suspend fun checkIp(request: CheckIpRequest): NetworkResponseWrapper<Any>{
        val api = retrofit.create<Api>()
        return doRequest { api.checkIp(request) }
    }
}