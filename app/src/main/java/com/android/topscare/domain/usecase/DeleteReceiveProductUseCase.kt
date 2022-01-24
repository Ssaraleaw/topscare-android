package com.android.topscare.domain.usecase

import com.android.topscare.domain.data.repository.TopsCareRepository
import com.android.topscare.domain.model.DeleteRequest
import javax.inject.Inject

class DeleteReceiveProductUseCase @Inject constructor(
    private val repository: TopsCareRepository
){
    suspend operator fun invoke(deleteRequest: DeleteRequest) : Boolean {
        return repository.deleteReceiveProduct(deleteRequest)?.resultCode == 200
    }
}