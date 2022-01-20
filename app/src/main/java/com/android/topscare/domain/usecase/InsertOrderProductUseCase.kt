package com.android.topscare.domain.usecase

import com.android.topscare.domain.data.repository.TopsCareRepository
import com.android.topscare.domain.model.OrderRequest
import javax.inject.Inject

class InsertOrderProductUseCase @Inject constructor(
    private val repository: TopsCareRepository
){
    suspend operator fun invoke(orderRequest: OrderRequest) : Boolean {
        return repository.insertOrderProduct(orderRequest)?.resultCode == 200
    }
}