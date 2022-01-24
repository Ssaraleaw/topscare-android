package com.android.topscare.domain.data.repository

import com.android.topscare.domain.data.remote.Api
import com.android.topscare.domain.model.*
import com.android.topscare.lib_base.network.NetworkResponseWrapper
import com.android.topscare.lib_base.network.doRequest
import javax.inject.Inject

class TopsCareRepository @Inject constructor(
    private val api: Api
){
    suspend fun getProductByKey(key: String): ProductResponse?{
        return doRequest { api.searchProduct(key = key).data }
    }
    suspend fun getProductCount(limit: Int, page: Int, key: String?): List<CountResponse>?{
        return doRequest { api.searchProductCount(limit, page, key).data }
    }
    suspend fun getProductOrder(limit: Int, page: Int, key: String?): List<OrderHistoryResponse>?{
        return doRequest { api.searchProductOrder(limit, page, key).data }
    }
    suspend fun getProductReceive(limit: Int, page: Int, key: String?): List<ReceiveHistoryResponse>?{
        return doRequest { api.searchProductReceive(limit, page, key).data }
    }

    suspend fun insertCountProduct(request: CountRequest): NetworkResponseWrapper<Any>{
        return doRequest { api.addCountProduct(request) }
    }
    suspend fun deleteCountProduct(request: DeleteRequest): NetworkResponseWrapper<Any>{
        return doRequest { api.deleteCountProduct(request) }
    }
    suspend fun deleteOrderProduct(request: DeleteRequest): NetworkResponseWrapper<Any>{
        return doRequest { api.deleteOrderProduct(request) }
    }
    suspend fun deleteReceiveProduct(request: DeleteRequest): NetworkResponseWrapper<Any>{
        return doRequest { api.deleteReceiveProduct(request) }
    }
    suspend fun insertOrderProduct(request: OrderRequest): NetworkResponseWrapper<Any>{
        return doRequest { api.insertOrderProduct(request) }
    }
    suspend fun insertReceiveProduct(request: ReceiveRequest): NetworkResponseWrapper<Any>{
        return doRequest { api.insertReceiveProduct(request) }
    }
}