package com.android.topscare.domain.usecase

import com.android.topscare.domain.data.repository.TopsCareRepository
import com.android.topscare.domain.model.CountRequest
import javax.inject.Inject

class InsertCountProductUseCase @Inject constructor(
    private val repository: TopsCareRepository
){
    suspend operator fun invoke(countRequest: CountRequest) : Boolean {
        return repository.insertCountProduct(countRequest)?.resultCode == 200
    }
}