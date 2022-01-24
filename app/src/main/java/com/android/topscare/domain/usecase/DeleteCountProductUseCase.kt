package com.android.topscare.domain.usecase

import com.android.topscare.domain.data.repository.TopsCareRepository
import com.android.topscare.domain.model.DeleteCountRequest
import javax.inject.Inject

class DeleteCountProductUseCase @Inject constructor(
    private val repository: TopsCareRepository
){
    suspend operator fun invoke(deleteCountRequest: DeleteCountRequest) : Boolean {
        return repository.deleteCountProduct(deleteCountRequest)?.resultCode == 200
    }
}