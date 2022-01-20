package com.android.topscare.domain.data.repository

import com.android.topscare.domain.data.remote.Api
import com.android.topscare.domain.model.CountRequest
import com.android.topscare.domain.model.OrderRequest
import com.android.topscare.domain.model.ProductResponse
import com.android.topscare.domain.model.ReceiveRequest
import com.android.topscare.lib_base.network.NetworkResponseWrapper
import com.android.topscare.lib_base.network.doRequest
import javax.inject.Inject

class TopsCareRepository @Inject constructor(
    private val api: Api
){
    suspend fun getProductByKey(key: String): ProductResponse?{
        return doRequest { api.searchProduct(key = key).data }
    }
    suspend fun insertCountProduct(request: CountRequest): NetworkResponseWrapper<Any>{
        return doRequest { api.addCountProduct(request) }
    }
    suspend fun insertOrderProduct(request: OrderRequest): NetworkResponseWrapper<Any>{
        return doRequest { api.insertOrderProduct(request) }
    }
    suspend fun insertReceiveProduct(request: ReceiveRequest): NetworkResponseWrapper<Any>{
        return doRequest { api.insertReceiveProduct(request) }
    }
}