package com.android.topscare.domain.data.repository

import com.android.topscare.domain.data.remote.Api
import com.android.topscare.domain.model.ProductResponse
import com.android.topscare.lib_base.network.doRequest
import javax.inject.Inject

class TopsCareRepository @Inject constructor(
    private val api: Api
){
    suspend fun getProductByKey(key: String): ProductResponse?{
        return doRequest { api.searchProduct(key = key).data }
    }
}