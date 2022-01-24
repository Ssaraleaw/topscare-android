package com.android.topscare.domain.usecase

import com.android.topscare.domain.data.repository.TopsCareRepository
import com.android.topscare.domain.model.DeleteRequest
import javax.inject.Inject

class DeleteOrderProductUseCase @Inject constructor(
    private val repository: TopsCareRepository
){
    suspend operator fun invoke(deleteRequest: DeleteRequest) : Boolean {
        return repository.deleteOrderProduct(deleteRequest)?.resultCode == 200
    }
}