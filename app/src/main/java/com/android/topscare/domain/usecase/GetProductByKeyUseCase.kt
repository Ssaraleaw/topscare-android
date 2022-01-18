package com.android.topscare.domain.usecase

import com.android.topscare.domain.data.repository.TopsCareRepository
import com.android.topscare.domain.model.ProductResponse
import javax.inject.Inject

class GetProductByKeyUseCase @Inject constructor(
    private val repository: TopsCareRepository
){
    suspend operator fun invoke(input: String) : ProductResponse {
        return repository.getProductByKey(input)
    }
}